package com.newpath.jeg.habbitnow.repository

import androidx.lifecycle.LiveData
import com.newpath.jeg.habbitnow.database.HabitDatabaseDao
import com.newpath.jeg.habbitnow.models.MyHabit

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class MyHabitsRepository(private val habitDao: HabitDatabaseDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allHabits: LiveData<List<MyHabit>> = habitDao.getAllHabits()

    suspend fun insert(habit: MyHabit) {
        habitDao.insert(habit)
    }

    suspend fun update(habit: MyHabit){
        habitDao.update(habit)
    }

    suspend fun delete(habit: MyHabit){
        habitDao.delete(habit)
    }
}