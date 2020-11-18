package com.newpath.jeg.habbitnow.utils

import android.content.Context
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.utils.ByteManipulator.Companion.isBitSet

/**
 * Builds string values based on numerical data i.e 1 = monday, or 12 = december
 */
class StringGenerator {

    companion object{

        /**
         * builds a string of week days depending on corresponding bits.
         * i.e if byte = 00000001, then it'll return sunday. If byte = 00000011,
         * it'll return monday sunday
         */
        fun getDaysFromByte(BYTE: Byte, ctx: Context): String{
            var daysOfWeek: Array<String> = ctx.resources.getStringArray(R.array.daysOfWeek)
            var daysActive: String = ""

            for (i in 0 until 7) {
                if (isBitSet(BYTE,i))
                    daysActive+=daysOfWeek[i] + " "
            }

            return daysActive.trim()
        }

        fun minutesToHours(minutes: Int): String{
            var hours: Int = 0
            var remainingMinutes: Int = minutes

            while(remainingMinutes>59){
                remainingMinutes-=60
                hours++
            }

            var minutesFormatted: String = ""
            if (hours==1)
                minutesFormatted = "$hours Hour"
            else if (hours>1)
                minutesFormatted = "$hours Hours"
            if (remainingMinutes>0 && minutesFormatted.isNotBlank())
                minutesFormatted += " and $remainingMinutes Minutes"
            else if (remainingMinutes>0)
                minutesFormatted = "$remainingMinutes Minutes"

            return minutesFormatted
        }

        fun getTime(alarmHour: Int, mins: Int): String{

            val formatMinutes: String = String.format("%02d", mins)

            if (alarmHour == 0) {
                return "${(alarmHour + 12)}:$formatMinutes AM"
            } else if (alarmHour > 12) {     //for military time that is 13:00 or larger, subtract 12 hours to get standard time
                return "${(alarmHour - 12)}:$formatMinutes PM"
            } else {
                return "$alarmHour:$formatMinutes AM"
            }
        }
    }

}