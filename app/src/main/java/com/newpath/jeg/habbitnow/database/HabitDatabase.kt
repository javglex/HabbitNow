package com.newpath.jeg.habbitnow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.newpath.jeg.habbitnow.models.MyHabit

@Database(entities = [MyHabit::class], version = 2, exportSchema = false)
abstract class HabitDatabase: RoomDatabase() {

    abstract val habitDatabaseDao: HabitDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getInstance(context: Context): HabitDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HabitDatabase::class.java,
                        "habit_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}