package com.newpath.jeg.habbitnow.constants

class AlarmConstants {
    public class AlarmType{
        companion object {
            public const val ALARM: Int = 0 //this will fire an alarm to really get user's attention
            public const val NOTIFICATION: Int = 1 //this will fire a notification that serves as a gentle reminder
        }
    }
}