package com.newpath.jeg.habbitnow.ui.edithabit

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.newpath.jeg.habbitnow.database.HabitDatabase
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.repository.MyHabitsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditHabitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MyHabitsRepository
    private val TAG: String = "EditHabitViewModel"
    var mHabitName: String = "New Habit"
    var mHabitId: Long? = null

    init {
        val habitDao = HabitDatabase.getInstance(application).habitDatabaseDao
        repository = MyHabitsRepository(habitDao)
        Log.d(TAG,"MainActivity ViewModel initialized")
    }

    fun submitHabit(){
        if (mHabitId==null) {   //if args were not passed (not editing a habit)
            Log.d(TAG,"creating new habit")
            val habit = MyHabit()
            habit.habitName = mHabitName;
            insertHabit(habit)
        } else
        {
            Log.d(TAG,"updating existing habit")
            val habit = MyHabit()
            habit.id = mHabitId!!
            habit.habitName = mHabitName
            updateHabit(habit)
        }

    }

    /**
     * Launching a new coroutine to update the data in a non-blocking way
     */
    private fun updateHabit(habit: MyHabit) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(habit)
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    private fun insertHabit(habit: MyHabit) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(habit)
    }

}