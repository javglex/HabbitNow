package com.newpath.jeg.habbitnow

import com.newpath.jeg.habbitnow.models.MyHabit
import com.newpath.jeg.habbitnow.utils.ByteManipulator
import com.newpath.jeg.habbitnow.utils.MyHabitCalendarHelper
import com.newpath.jeg.habbitnow.utils.MyHabitCalendarHelper.Companion.getHabitTimeInMillis
import com.newpath.jeg.habbitnow.utils.MyHabitCalendarHelper.Companion.getNextDayMillis
import junit.framework.Assert.assertEquals
import org.junit.Test

class CalendarUnitTest {

    @Test
    fun mondayDiffTuesdayGet86k(){
        var day1: Int = 1
        var day2: Int = 2
        var diff: Long = MyHabitCalendarHelper.getMilliDiffBetweenWeekDays(day1, day2)

        assertEquals(diff, 86400000)
    }

    @Test
    fun mondayDiffWedGet132k(){
        var day1: Int = 1
        var day2: Int = 3
        var diff: Long = MyHabitCalendarHelper.getMilliDiffBetweenWeekDays(day1, day2)

        assertEquals(diff, 86400000 * 2)
    }

    @Test
    fun tueDiffMonGet86k(){
        var day1: Int = 2
        var day2: Int = 1
        var diff: Long = MyHabitCalendarHelper.getMilliDiffBetweenWeekDays(day1, day2)

        assertEquals(diff, 86400000)
    }

    @Test
    fun habitAlarm830pGetInMillis(){

        var habit: MyHabit = MyHabit()
        habit.alarmTimeHours = 8
        habit.alarmTimeMinutes = 30
        var timeInMillis: Long = getHabitTimeInMillis(habit)
        var currTime: Long = getHabitTimeInMillis(MyHabit(alarmTimeHours = 0, alarmTimeMinutes = 0))
        assertEquals(timeInMillis, currTime + 30600000)
    }

    @Test
    fun habitAlarm830pNextActive24hrs(){
        var habit: MyHabit = MyHabit()
        habit.alarmTimeHours = 8
        habit.alarmTimeMinutes = 30
        var nextDayMillis = getNextDayMillis(habit)
        var currTimeMillis = getHabitTimeInMillis(habit)
        //no repeat days set, should get currtime + 24hours
        assertEquals(nextDayMillis, currTimeMillis + MyHabitCalendarHelper.DAYINMILLIS)
    }

    @Test
    fun habitAlarm830pNextActive(){ //TODO: this test will fail on a different day
        var habit: MyHabit = MyHabit()
        habit.alarmTimeHours = 8
        habit.alarmTimeMinutes = 30
        habit.daysActive = ByteManipulator.setBit(habit.daysActive, 3) //setting wednesday
        var nextDayMillis = getNextDayMillis(habit)
        var currTimeMillis = getHabitTimeInMillis(habit)

        assertEquals(nextDayMillis, currTimeMillis + (MyHabitCalendarHelper.DAYINMILLIS*4))
    }


}