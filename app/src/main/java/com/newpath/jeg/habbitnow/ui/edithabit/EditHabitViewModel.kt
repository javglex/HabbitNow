package com.newpath.jeg.habbitnow.ui.edithabit

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.newpath.jeg.habbitnow.constants.AlarmConstants
import com.newpath.jeg.habbitnow.database.HabitDatabase
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.repository.MyHabitsRepository
import com.newpath.jeg.habbitnow.services.AlarmService
import com.newpath.jeg.habbitnow.utils.ByteManipulator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class EditHabitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MyHabitsRepository
    private val TAG: String = "EditHabitViewModel"
    var mHabitName: String = "New Habit"
    var mHabitId: Long? = null
    var mHabitDaysRepeat: Byte = 0b00000000
    var mHabitIntervalMillis: Long = 0L
    var mHabitAlarmHour: Int = 0
    var mHabitAlarmMin: Int = 0
    var mHabitType: Int = AlarmConstants.AlarmType.ALARM

    init {
        val habitDao = HabitDatabase.getInstance(application).habitDatabaseDao
        repository = MyHabitsRepository(habitDao)
        Log.d(TAG,"MainActivity ViewModel initialized")
    }

    fun submitHabit(){

        val habit = MyHabit()
        habit.habitName = mHabitName
        habit.daysActive = mHabitDaysRepeat
        habit.alarmTimeMinutes = mHabitAlarmMin
        habit.alarmTimeHours = mHabitAlarmHour
        habit.alarmType = mHabitType
        habit.alarmIntervalMillis = mHabitIntervalMillis

        if (mHabitId==null) {   //if args were not passed (not editing a habit)
            Log.d(TAG,"creating new habit")
            insertHabit(habit)
        } else
        {
            Log.d(TAG,"updating existing habit")
            habit.id = mHabitId!!
            updateHabit(habit)
        }

    }

    fun setInterval(mins: Int){
        mHabitIntervalMillis = (mins*1000).toLong() //1000 to convert to millis. 3 just to have 15 minutes
    }

    fun setDay(day: Int){
        mHabitDaysRepeat = ByteManipulator.setBit(mHabitDaysRepeat,day)
    }

    fun unsetDay(day: Int){
        mHabitDaysRepeat = ByteManipulator.unsetBit(mHabitDaysRepeat, day)
    }

    /**
     * Launching a new coroutine to update the data in a non-blocking way
     */
    private fun updateHabit(habit: MyHabit) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(habit, getApplication())
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    private fun insertHabit(habit: MyHabit) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(habit, getApplication())
    }


}