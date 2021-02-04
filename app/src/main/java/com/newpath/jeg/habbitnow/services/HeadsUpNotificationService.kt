package com.newpath.jeg.habbitnow.services

import android.app.*
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import com.newpath.jeg.habbitnow.AlarmActivity
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.constants.AlarmConstants
import com.newpath.jeg.habbitnow.constants.NotificationConstants.ActionKey.Companion.EXTRA_ALARM_ID_KEY
import com.newpath.jeg.habbitnow.constants.NotificationConstants.ActionType.Companion.ACCEPT_ACTION_KEY
import com.newpath.jeg.habbitnow.constants.NotificationConstants.ActionType.Companion.DEFER_ACTION_KEY
import com.newpath.jeg.habbitnow.database.HabitDatabase
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.receivers.HeadsUpNotificationActionReceiver
import com.newpath.jeg.habbitnow.repository.MyHabitsRepository
import com.newpath.jeg.habbitnow.services.AlarmService.Companion.ALARM_ID
import com.newpath.jeg.habbitnow.services.AlarmService.Companion.ALARM_NAME
import com.newpath.jeg.habbitnow.services.AlarmService.Companion.ALARM_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HeadsUpNotificationService : Service() {

    private val CHANNEL_ID = "AlarmChannel"
    private val CHANNEL_NAME = "Alarm Channel"
    private val TAG: String = "HeadsUpNotificationService"

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        var alarmid: Long? = null
        var alarmName: String? = null
        var alarmType: Int? = 0

        if (intent != null && intent.extras != null) {
            alarmid = intent.getLongExtra(ALARM_ID, -1)
            alarmName = intent.getStringExtra(ALARM_NAME)
            alarmType = intent.getIntExtra(ALARM_TYPE,0)
        }
        Log.i(TAG, "Got alarmid as $alarmid")

        if (alarmid==null || alarmid.equals(-1) )
            return START_NOT_STICKY

        try {

            val receiveCallAction =
                Intent(applicationContext, HeadsUpNotificationActionReceiver::class.java)
            receiveCallAction.putExtra(
                EXTRA_ALARM_ID_KEY,
                ACCEPT_ACTION_KEY
            )
            receiveCallAction.putExtra(EXTRA_ALARM_ID_KEY, alarmid)
            receiveCallAction.action = "RECEIVE_CALL"

            val cancelCallAction =
                Intent(applicationContext, HeadsUpNotificationActionReceiver::class.java)
            cancelCallAction.putExtra(
                EXTRA_ALARM_ID_KEY,
                DEFER_ACTION_KEY
            )
            cancelCallAction.putExtra(EXTRA_ALARM_ID_KEY, alarmid)
            cancelCallAction.action = "CANCEL_CALL"

            val receiveCallPendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                1200,
                receiveCallAction,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val cancelCallPendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                1201,
                cancelCallAction,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val fullScreenIntent = Intent(applicationContext, AlarmActivity::class.java)
            val fullScreenPendingIntent = PendingIntent.getActivity(
                applicationContext, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            createChannel()

            var PRIORITY: Int = if (alarmType == AlarmConstants.AlarmType.ALARM)
                NotificationCompat.PRIORITY_HIGH
            else NotificationCompat.PRIORITY_DEFAULT

            var CATEGORY: String = if (alarmType == AlarmConstants.AlarmType.ALARM)
                NotificationCompat.CATEGORY_CALL
            else NotificationCompat.CATEGORY_EVENT

            Log.d(TAG, "noti priority: $PRIORITY cat: $CATEGORY")

            var notificationBuilder: NotificationCompat.Builder? = null

            notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentText("Time to make change!")
                .setContentTitle(alarmName)
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setPriority(PRIORITY)
                .setCategory(CATEGORY)
                .addAction(R.drawable.ic_baseline_alarm_24, "On it!", receiveCallPendingIntent)
                .addAction(R.drawable.ic_baseline_notifications_active_24, "Not this time", cancelCallPendingIntent)
                .setAutoCancel(true)
            if (alarmType == AlarmConstants.AlarmType.ALARM)
                notificationBuilder.setFullScreenIntent(fullScreenPendingIntent, true)


            var incomingHabitNotification: Notification? = null
            if (notificationBuilder != null) {
                incomingHabitNotification = notificationBuilder.build()
            }
            val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager?

//            startForeground(120, incomingCallNotification)
            notificationManager?.notify(120, incomingHabitNotification)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return START_NOT_STICKY
    }

    private suspend fun getHabitData(alarmId: Long): MyHabit?{

        //get notification data
        val habitDao = HabitDatabase.getInstance(applicationContext).habitDatabaseDao
        val habit: MyHabit? = MyHabitsRepository(habitDao).getHabit(alarmId)
        if (habit==null) {
            Log.e(TAG, "Empty habit!")
            return null
        }

        Log.i(TAG, "Habit is valid!")

        return habit
    }

    /**
     * for greater than or equal to Oreo
     */
    private fun createChannel() {
        val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name: CharSequence = "test" // The user-visible name of the channel.
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)

            notificationManager?.createNotificationChannel(mChannel)
        }
    }
}

//class NotificationService {
//
//    companion object{
//
//        private const val CHANNEL_ID: String = "Test"
//        private const val TAG: String = "NotificationService"
//
//        public suspend fun fireHabitNotification(
//            context: Context,
//            alarmId: Long,
//            intentService: IntentService
//        ){
//
//            //get notification data
//            val habitDao = HabitDatabase.getInstance(context).habitDatabaseDao
//            val habit: MyHabit? = MyHabitsRepository(habitDao).getHabit(alarmId)
//            if (habit==null) {
//                Log.e(TAG, "Empty habit!")
//                return
//            }
//
//            Log.i(TAG, "Habit is valid!")
//
//
//            var PRIORITY: Int = if (habit.alarmType == AlarmConstants.AlarmType.ALARM)
//                NotificationCompat.PRIORITY_HIGH
//            else NotificationCompat.PRIORITY_DEFAULT
//
//            var CATEGORY: String = if (habit.alarmType == AlarmConstants.AlarmType.ALARM)
//                NotificationCompat.CATEGORY_CALL
//            else NotificationCompat.CATEGORY_EVENT
//
//            Log.d(TAG, "noti priority: $PRIORITY cat: $CATEGORY")
//
//
//            val fullScreenIntent = Intent(context, AlarmActivity::class.java)
//            val fullScreenPendingIntent = PendingIntent.getActivity(
//                context, 0,
//                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT
//            )
//
//            val notificationBuilder =
//                NotificationCompat.Builder(context, CHANNEL_ID)
//                    .setSmallIcon(R.drawable.ic_baseline_alarm_24)
//                    .setContentTitle(habit.habitName)
//                    .setContentText("Got a habit yo!")
//                    .setPriority(PRIORITY)
//                    .setCategory(CATEGORY)
//
//                    // Use a full-screen intent only for the highest-priority alerts where you
//                    // have an associated activity that you would like to launch after the user
//                    // interacts with the notification. Also, if your app targets Android 10
//                    // or higher, you need to request the USE_FULL_SCREEN_INTENT permission in
//                    // order for the platform to invoke this notification.
//                    .setFullScreenIntent(fullScreenPendingIntent, true)
//
//            val incomingAlarmNotification = notificationBuilder.build()
//            // Provide a unique integer for the "notificationId" of each notification.
//            Log.d(TAG, "launching foreground..")
//            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                val name: CharSequence = "test" // The user-visible name of the channel.
//                val importance = NotificationManager.IMPORTANCE_HIGH
//                val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
//
//                notificationManager?.createNotificationChannel(mChannel)
//            }
//
//            notificationManager?.notify(alarmId.toInt(), incomingAlarmNotification)
////            intentService.startForeground(1, incomingAlarmNotification)
//        }
//
//    }
//}