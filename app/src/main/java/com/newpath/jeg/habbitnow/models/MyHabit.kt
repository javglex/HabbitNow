package com.newpath.jeg.habbitnow.models

import com.newpath.jeg.habbitnow.constants.AlarmConstants

class MyHabit {
    lateinit var id: String
    var habitName: String = ""
    var daysActive: ByteArray = byteArrayOf(0,0,0,0,0,0,0)
    var alarmType: Int = AlarmConstants.AlarmType.DEFAULT
}