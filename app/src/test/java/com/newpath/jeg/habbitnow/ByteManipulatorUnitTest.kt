package com.newpath.jeg.habbitnow

import com.newpath.jeg.habbitnow.utils.ByteManipulator
import com.newpath.jeg.habbitnow.utils.ByteManipulator.Companion.isBitSet
import org.junit.Assert
import org.junit.Test

class ByteManipulatorUnitTest {
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
        Assert.assertEquals(b,false)
        b = isBitSet(daysActive,-200)  //should stop shifting at 8th bit
        Assert.assertEquals(b,true)
    }

    @Test
    fun setBit() {
        var daysActive: Byte = 0b01010101
        daysActive = ByteManipulator.setBit(daysActive, 1)
        Assert.assertEquals(daysActive.toByte(), (0b01010111).toByte())
        daysActive = ByteManipulator.setBit(daysActive, 0)
        Assert.assertEquals(daysActive.toByte(), (0b01010111).toByte())
        daysActive = ByteManipulator.setBit(daysActive, 3)
        Assert.assertEquals(daysActive.toByte(), (0b01011111).toByte())
        daysActive = ByteManipulator.setBit(daysActive, 5)
        Assert.assertEquals(daysActive.toByte(), (0b01111111).toByte())
    }

    @Test
    fun setBitOutOfbounds() {
        var daysActive: Byte = 0b01010101
        daysActive = ByteManipulator.setBit(daysActive, 9)
        Assert.assertEquals(daysActive.toByte(), (0b01010101).toByte())
        daysActive = ByteManipulator.setBit(daysActive, -1)
        Assert.assertEquals(daysActive.toByte(), (0b01010101).toByte())
    }

    @Test
    fun unsetBit() {
        var daysActive: Byte = 0b01010111
        daysActive = ByteManipulator.unsetBit(daysActive, 1)
        Assert.assertEquals(daysActive.toByte(), (0b01010101).toByte())
        daysActive = ByteManipulator.unsetBit(daysActive, 0)
        Assert.assertEquals(daysActive.toByte(), (0b01010100).toByte())
        daysActive = ByteManipulator.unsetBit(daysActive, 3)
        Assert.assertEquals(daysActive.toByte(), (0b01010100).toByte())
        daysActive = ByteManipulator.unsetBit(daysActive, 5)
        Assert.assertEquals(daysActive.toByte(), (0b01010100).toByte())
    }

    @Test
    fun unsetBitOutOfbounds() {
        var daysActive: Byte = 0b01010101
        daysActive = ByteManipulator.unsetBit(daysActive, 9)
        Assert.assertEquals(daysActive.toByte(), (0b01010101).toByte())
        daysActive = ByteManipulator.unsetBit(daysActive, -1)
        Assert.assertEquals(daysActive.toByte(), (0b01010101).toByte())
    }

}