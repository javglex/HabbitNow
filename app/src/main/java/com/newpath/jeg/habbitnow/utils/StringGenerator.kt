package com.newpath.jeg.habbitnow.utils

import android.content.Context
import com.newpath.jeg.habbitnow.R
import com.newpath.jeg.habbitnow.utils.ByteReader.Companion.isBitSet

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
    }

}