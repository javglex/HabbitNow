package com.newpath.jeg.habbitnow.services

import android.app.AlarmManager
import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

/**
 * Created by javier gon on 6/6/2017.
 */
class AlarmService: IntentService(TAG) {

    override fun onHandleIntent(intent: Intent?) {
        Log.i(TAG, "received an intent: $intent")
    }

    companion object {
        const val TAG = "AlarmService" //for logging and passing a name to base class IntentService

        private fun newIntent(context: Context?): Intent {
            return Intent(context, AlarmService::class.java)
        }

        fun setServiceAlarm(
            context: Context,
            requestCode: Int,
            alarmTime: Calendar
        ) {
            val intent = newIntent(context)
            val pi = PendingIntent.getService(context, requestCode, intent, 0)
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                alarmTime.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pi
            )
            Log.i(
                TAG,
                "set alarm for requestCode: " + requestCode + "at time: " + alarmTime.time.toString()
            )
        }

        fun clearServiceAlarm(context: Context, requestCode: Int){
            val intent = newIntent(context)
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val pi = PendingIntent.getService(context, requestCode, intent, 0)
            alarmManager.cancel(pi)
            pi.cancel()
        }

    }
}
