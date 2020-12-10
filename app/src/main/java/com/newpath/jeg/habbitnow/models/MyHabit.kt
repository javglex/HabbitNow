package com.newpath.jeg.habbitnow.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.newpath.jeg.habbitnow.constants.AlarmConstants

@Entity(tableName = "habits_table")
data class MyHabit(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "habit_name")
    var habitName: String = "",
    @ColumnInfo(name = "days_active")
    var daysActive: Byte = 0b00000000, //last 7 bytes represent a day of the week
    @ColumnInfo(name = "alarm_type")
    var alarmType: Int = AlarmConstants.AlarmType.ALARM,
    @ColumnInfo(name = "alarm_hours")
    var alarmTimeHours: Int = 0, //hour that alarm is set to, 24 hour format
    @ColumnInfo(name = "alarm_minutes")
    var alarmTimeMinutes: Int = 0, //minute that alarm is set to
    @ColumnInfo(name = "alarm_interval_millis")
    var alarmIntervalMillis: Long = 0

)