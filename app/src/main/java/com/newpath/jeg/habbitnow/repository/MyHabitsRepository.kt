package com.newpath.jeg.habbitnow.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.newpath.jeg.habbitnow.database.HabitDatabaseDao
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.services.AlarmService
import java.util.*
import java.util.concurrent.Callable

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class MyHabitsRepository(private val habitDao: HabitDatabaseDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allHabits: LiveData<List<MyHabit>> = habitDao.getAllHabits()

    suspend fun insert(habit: MyHabit, context: Context){
        var id: Long = habitDao.insert(habit)       //TODO: Find a way to loop these ID's (after exceeding int length) since our intent request codes don't support long
        habit.id = id
        updateAlarmService(habit,context)
    }

    suspend fun update(habit: MyHabit,  context: Context){
        habitDao.update(habit)
        updateAlarmService(habit,context)
    }

    suspend fun delete(habit: MyHabit, context: Context){
        habitDao.delete(habit)
        deleteAlarmIntent(habit, context)
    }

    private fun updateAlarmService(habit: MyHabit, context: Context){

        if (habit == null)
            return

        val alarmTime = Calendar.getInstance()
        alarmTime[Calendar.HOUR_OF_DAY] = habit.alarmTimeHours
        alarmTime[Calendar.MINUTE] = habit.alarmTimeMinutes
        alarmTime[Calendar.SECOND] = 0
        AlarmService.setServiceAlarm(context, habit.id.toInt(), alarmTime)

    }

    private fun deleteAlarmIntent(habit: MyHabit, context: Context){
        val alarmTime = Calendar.getInstance()
        alarmTime[Calendar.HOUR_OF_DAY] = habit.alarmTimeHours
        alarmTime[Calendar.MINUTE] = habit.alarmTimeMinutes
        alarmTime[Calendar.SECOND] = 0
        AlarmService.clearServiceAlarm(context, habit.id.toInt())
    }

}