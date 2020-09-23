package com.newpath.jeg.habbitnow

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.newpath.jeg.habbitnow.database.HabitDatabase
import com.newpath.jeg.habbitnow.database.HabitDatabaseDao
import com.newpath.jeg.habbitnow.models.MyHabit
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class HabitDatabaseTest {

    private lateinit var habitDatabaseDao: HabitDatabaseDao
    private lateinit var db: HabitDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, HabitDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        habitDatabaseDao = db.habitDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetHabit() {
        val habit = MyHabit()
        habitDatabaseDao.insert(habit)
        val firstHabit = habitDatabaseDao.getFirstHabit()
        assertEquals(firstHabit?.habitName, "")
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetDaysActive() {
        val habit = MyHabit()
        habitDatabaseDao.insert(habit)
        val firstHabit = habitDatabaseDao.getFirstHabit()
        var daysActive: Byte = 0b01010101
        assertEquals(firstHabit?.daysActive, daysActive)
    }


}