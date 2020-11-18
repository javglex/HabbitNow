package com.newpath.jeg.habbitnow.services

import android.app.AlarmManager
import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.newpath.jeg.habbitnow.AlarmActivity
import com.newpath.jeg.habbitnow.MainActivity
import com.newpath.jeg.habbitnow.database.HabitDatabase
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.repository.MyHabitsRepository
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext


/**
 * Created by javier gon on 6/6/2017.
 */
class AlarmService: IntentService(TAG) {



    override fun onHandleIntent(intent: Intent?) {
        if (intent!=null) {
            val alarmId: Long = intent.getLongExtra(ALARM_ID, -1)
            Log.i(TAG, "received an intent: $alarmId")
            if (alarmId < 0)
                return

//            GlobalScope.launch(Dispatchers.IO) {
//                //send user notification
//                NotificationService.fireHabitNotification(baseContext, alarmId, this@AlarmService)
//            }

            val notifServiceIntent = Intent(baseContext, HeadsUpNotificationService::class.java)
            notifServiceIntent.putExtra(ALARM_NAME, intent.getStringExtra(ALARM_NAME))
            notifServiceIntent.putExtra(ALARM_ID, intent.getLongExtra(ALARM_ID,-1))
            notifServiceIntent.putExtra(ALARM_TYPE, intent.getIntExtra(ALARM_TYPE,0))
            application.startService(notifServiceIntent)
        }
    }

    companion object {
        const val TAG = "AlarmService" //for logging and passing a name to base class IntentService
        const val ALARM_NAME = "ALARM_NAME"
        const val ALARM_ID = "ALARM_ID"
        const val ALARM_TYPE = "ALARM_TYPE"

        private fun newIntent(context: Context?): Intent {
            return Intent(context, AlarmService::class.java)
        }

        fun setServiceAlarm(
            context: Context,
            habit: MyHabit,
            alarmTime: Calendar
        ) {
            val intent = newIntent(context)
            intent.putExtra(ALARM_ID, habit.id)
            intent.putExtra(ALARM_NAME, habit.habitName)
            intent.putExtra(ALARM_TYPE, habit.alarmType)
            intent.action = "ALARM$habit.id"

            val pi = PendingIntent.getService(context, 0, intent, 0)
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                alarmTime.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pi
            )

            Log.i(
                TAG,
                "set alarm with id: " + habit.id + "at time: " + alarmTime.time.toString()
            )
        }

        fun clearServiceAlarm(context: Context, requestCode: Int){
            val intent = newIntent(context)
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val pi = PendingIntent.getService(context, requestCode, intent, 0)
            alarmManager.cancel(pi)
            pi.cancel()
            Log.i(TAG, "cancelling pending intent.. $requestCode")
        }


    }
}
