package com.newpath.jeg.habbitnow.database

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.*
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
    fun getAllHabits(): LiveData<List<MyHabit>>     //room will keep the data fresh, so you only need to call this function once. WOW!

    /*
  * delete the object from database
  * @param note, object to be deleted
  */
    @Delete
    fun delete(habit: MyHabit?)
}