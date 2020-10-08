package com.newpath.jeg.habbitnow

import com.newpath.jeg.habbitnow.utils.ByteReader.Companion.isBitSet
import org.junit.Assert
import org.junit.Test

class ByteReaderUnitTest {
    @Test
    fun bitset0_isCorrect() {
        var daysActive: Byte = 0b01010101 //last 7 bytes represent a day of the week
        var b : Boolean = isBitSet(daysActive,0)
        Assert.assertEquals(b, true)
    }

    @Test
    fun bitset1_isCorrect() {
        var daysActive: Byte = 0b01010101
        var b : Boolean = isBitSet(daysActive,1)
        Assert.assertEquals(b, false)
    }

    @Test
    fun bitset2_isCorrect() {
        var daysActive: Byte = 0b01010101
        var b : Boolean = isBitSet(daysActive,2)
        Assert.assertEquals(b, true)
    }

    @Test
    fun bitOutOfBounds(){
        var daysActive: Byte = 0b01010101
        var b : Boolean = isBitSet(daysActive,200)  //should stop shifting at 8th bit
        Assert.assertEquals(b,true)
    }
}