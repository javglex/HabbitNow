package com.newpath.jeg.habbitnow.utils

class NumberConvert {

    companion object{
        fun milliToMinutes(milli: Long): Int{
            if (milli < 500)
                return 0
            if (milli in 501..1000)
                return 1
            return (milli/1000).toInt()
        }
    }
}