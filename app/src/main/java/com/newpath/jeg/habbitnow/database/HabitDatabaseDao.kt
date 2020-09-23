package com.newpath.jeg.habbitnow.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.newpath.jeg.habbitnow.models.MyHabit

@Dao
interface HabitDatabaseDao {

    @Insert
    fun insert(habit: MyHabit)

    @Update
    fun update(habit: MyHabit)

    @Query("SELECT * from habits_table WHERE id = :key")
    fun get(key: Long): MyHabit?

    @Query("DELETE FROM habits_table")
    fun clear()

    @Query("SELECT * FROM habits_table ORDER BY id DESC LIMIT 1")
    fun getFirstHabit(): MyHabit?

    @Query("SELECT * FROM habits_table ORDER BY id DESC")
    fun getAllNights(): LiveData<List<MyHabit>>     //room will keep the data fresh, so you only need to call this function once. WOW!

}