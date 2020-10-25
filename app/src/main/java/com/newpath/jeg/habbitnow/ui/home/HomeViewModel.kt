package com.newpath.jeg.habbitnow.ui.home

import android.app.Application
import android.view.View
import androidx.fragment.app.findFragment
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.database.HabitDatabase
import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.repository.MyHabitsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MyHabitsRepository
    private val _navigateToEditHabit = MutableLiveData<MyHabit>()
    val navigateToEditHabit: LiveData<MyHabit>  //to be used to trigger a navigation event
        get() = _navigateToEditHabit

    val allHabits: LiveData<List<MyHabit>>

    init {
        val habitDao = HabitDatabase.getInstance(application).habitDatabaseDao
        repository = MyHabitsRepository(habitDao)
        allHabits = repository.allHabits
    }

    fun onNavigateToEditHabit(habit:MyHabit) {
        _navigateToEditHabit.value = habit
    }

    fun onNavigateToEditHabitComplete() {
        _navigateToEditHabit.value = null
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(habit: MyHabit) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(habit, getApplication())
    }

    fun update(habit: MyHabit) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(habit, getApplication())
    }

    fun delete(habit: MyHabit) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(habit, getApplication())
    }

}