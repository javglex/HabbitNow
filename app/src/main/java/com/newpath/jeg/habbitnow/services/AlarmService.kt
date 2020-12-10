package com.newpath.jeg.habbitnow.services

import android.app.AlarmManager
import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.utils.MyHabitCalendarHelper
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*


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
            notifServiceIntent.putExtra(ALARM_ID, intent.getLongExtra(ALARM_ID, -1))
            notifServiceIntent.putExtra(ALARM_TYPE, intent.getIntExtra(ALARM_TYPE, 0))
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
            alarmTimeMillis: Long
        ) {
            val appContext: Context = context.applicationContext
            val intent = newIntent(appContext)
            var currentTimeMillis = alarmTimeMillis
            intent.putExtra(ALARM_ID, habit.id)
            intent.putExtra(ALARM_NAME, habit.habitName)
            intent.putExtra(ALARM_TYPE, habit.alarmType)
            intent.action = "ALARM$habit.id"

            if (currentTimeMillis < System.currentTimeMillis()) { //time already passed, set it for the next available day
                Log.i(TAG, "set time already passed, setting alarm for next available day..")
                try {
                    currentTimeMillis = MyHabitCalendarHelper.getNextDayMillis(habit)
                }catch(e : Exception){
                    Log.e(TAG,e.localizedMessage)
                    return
                }
            }

            val pi = PendingIntent.getService(appContext, habit.id.toInt(), intent, 0)
            val alarmManager = appContext.getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                currentTimeMillis,
                habit.alarmIntervalMillis,
                pi
            )

            val calendar = Calendar.getInstance() //for debugging times set
            calendar.timeInMillis = currentTimeMillis

            Log.i(
                TAG,
                "set alarm with id: " + habit.id + " at time: " + calendar.time.toString()
                        + " with interval " + habit.alarmIntervalMillis
            )
        }

        fun clearServiceAlarm(context: Context, habit: MyHabit){
            val appContext: Context = context.applicationContext
            val intent = newIntent(appContext)
            intent.putExtra(ALARM_ID, habit.id)
            intent.putExtra(ALARM_NAME, habit.habitName)
            intent.putExtra(ALARM_TYPE, habit.alarmType)
            intent.action = "ALARM$habit.id"
            val alarmManager = appContext.getSystemService(ALARM_SERVICE) as AlarmManager
            val pi = PendingIntent.getService(appContext, habit.id.toInt(), intent, 0)

            alarmManager.cancel(pi)
            pi.cancel()
            Log.i(TAG, "cancelling pending intent.. ${habit.id.toInt()}")
        }


    }
}
