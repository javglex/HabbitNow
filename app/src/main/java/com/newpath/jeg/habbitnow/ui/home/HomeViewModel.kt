package com.newpath.jeg.habbitnow.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.newpath.jeg.habbitnow.database.HabitDatabase
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.repository.MyHabitsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MyHabitsRepository
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allHabits: LiveData<List<MyHabit>>

    init {
        val habitDao = HabitDatabase.getInstance(application).habitDatabaseDao
        repository = MyHabitsRepository(habitDao)
        allHabits = repository.allHabits
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(habit: MyHabit) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(habit)
    }

}