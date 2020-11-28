package com.newpath.jeg.habbitnow.receivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.newpath.jeg.habbitnow.database.HabitDatabase
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.repository.MyHabitsRepository
import com.newpath.jeg.habbitnow.services.AlarmService
import com.newpath.jeg.habbitnow.utils.MyHabitCalendarHelper
import java.util.*

/**
 * Pending intents are lost on device boot, so this class
 * reads through the alarms in the DB and re-registers them
 * in the Alarm Service.
 */
class DeviceBootReceiver : BroadcastReceiver() {

    private val TAG: String = "DeviceBootReceiver"

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            //reset alarms here
            val habitDao = HabitDatabase.getInstance(context).habitDatabaseDao
            val repository: MyHabitsRepository
            repository = MyHabitsRepository(habitDao)
            val allHabits: List<MyHabit>? = repository.allHabits.value

            updateAllAlarmService(allHabits, context)
        }

    }


    private fun updateAllAlarmService(habits: List<MyHabit>?, context: Context){

        if (habits == null || habits.isEmpty()) {
            Log.d(TAG,"no habits to update");
            return
        }

        habits.forEach { habit ->

            val alarmTime: Long = MyHabitCalendarHelper.getHabitTimeInMillis(habit)
            AlarmService.setServiceAlarm(context, habit, alarmTime)

        }


    }
}