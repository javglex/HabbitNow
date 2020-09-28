package com.newpath.jeg.habbitnow

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.newpath.jeg.habbitnow.database.HabitDatabase
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.repository.MyHabitsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MyHabitsRepository
    private val TAG: String = "MainActivityViewModel"

    init {
        val habitDao = HabitDatabase.getInstance(application).habitDatabaseDao
        repository = MyHabitsRepository(habitDao)
        Log.d(TAG,"MainActivity ViewModel initialized")
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun newHabit() = viewModelScope.launch(Dispatchers.IO) {
        val habit = MyHabit()
        habit.habitName = "test" + System.currentTimeMillis().toString()
        repository.insert(habit)
    }

}