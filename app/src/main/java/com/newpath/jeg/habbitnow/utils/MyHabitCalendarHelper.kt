package com.newpath.jeg.habbitnow.utils

import com.newpath.jeg.habbitnow.models.MyHabit
import java.util.*
import kotlin.jvm.Throws
import kotlin.math.abs

class MyHabitCalendarHelper {

    companion object{

        val DAYINMILLIS: Long = 86400000

        @Throws(java.lang.IllegalArgumentException::class)
        fun getHabitTimeInMillis(habit: MyHabit?): Long{

            if (habit==null)
                throw IllegalArgumentException()

            val alarmTime = Calendar.getInstance()
            alarmTime[Calendar.HOUR_OF_DAY] = habit.alarmTimeHours
            alarmTime[Calendar.MINUTE] = habit.alarmTimeMinutes
            alarmTime[Calendar.SECOND] = 0
            alarmTime[Calendar.MILLISECOND] = 0

            return alarmTime.timeInMillis
        }

        /**
         * returns the next active day + current time in milliseconds
         * next active day is the next repeat day set.
         * if no repeat day exists, it will return current alarm time +24 hours
         * For example, if current alarm time is 8:30p, and next active day
         * is a day after. It will return 8:30p + 24h in milliseconds
         */
        @Throws(IllegalArgumentException::class)
        fun getNextDayMillis(habit: MyHabit?): Long {

            if (habit == null)
                throw IllegalArgumentException()

            val currDay: Int? = getCurrentDay()
            val currTimeMillis: Long = getHabitTimeInMillis(habit)

            if (habit == null || currDay == null)
                return currTimeMillis + DAYINMILLIS //+ 24 hours in millis

            for (i in currDay+1 until 7) { //check from current weekday to end of week (saturday = 6)
                if (ByteManipulator.isBitSet(habit.daysActive, i)){
                    //return difference in days between curr day and next active day
                    return currTimeMillis + getMilliDiffBetweenWeekDays(currDay,i)
                }
            }
            //i.e bit set = 0 (sun), currday = 3(wed), eow = 6(sat), total weekdays = 7
            //7 + 0 = 7. 7 - 3 = 4.
            //bit set = 1 (mon)
            // 7 + 1 = 8. 8 - 3 = 5.
            for (i in 0 until currDay+1){ //check from beginning of next week (sunday = 0) to current weekday
                if (ByteManipulator.isBitSet(habit.daysActive, i)){
                    //return difference in days between curr day and next active day
                    return currTimeMillis + getMilliDiffBetweenWeekDays(currDay,(i+7))
                }
            }

            //else no day available, return next immediate day
            return currTimeMillis + DAYINMILLIS //+ 24 hours in millis
        }

        /**
         * Returns the next available day as an integer
         * To help with creating intent ID for an alarm that contains multiple days
         */
        fun getNextDayInt(){

        }

        fun getMilliDiffBetweenWeekDays(day1: Int, day2: Int): Long{
            return abs(day2-day1) *DAYINMILLIS
        }

        fun getCurrentDay(): Int? {
            val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            return day - 1
        }
    }

}