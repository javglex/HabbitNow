package com.newpath.jeg.habbitnow

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.newpath.jeg.habbitnow.utils.StringGenerator
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StringGeneratorTest {

    @Test
    fun isSunday(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        var daysOfWeek: Array<String> = context.resources.getStringArray(R.array.daysOfWeek)

        val days = StringGenerator.getDaysFromByte(0b00000001, context)
        Assert.assertEquals(days,daysOfWeek[0])
    }

    @Test
    fun isSunTue(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        var daysOfWeek: Array<String> = context.resources.getStringArray(R.array.daysOfWeek)

        val days = StringGenerator.getDaysFromByte(0b00000101, context)
        Assert.assertEquals(days,daysOfWeek[0]+" "+daysOfWeek[2])
    }

    @Test
    fun isSunTueThu(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        var daysOfWeek: Array<String> = context.resources.getStringArray(R.array.daysOfWeek)

        val days = StringGenerator.getDaysFromByte(0b00010101, context)
        Assert.assertEquals(days,daysOfWeek[0]+" "+daysOfWeek[2] + " " + daysOfWeek[4])
    }
}