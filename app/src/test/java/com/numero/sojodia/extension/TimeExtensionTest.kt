package com.numero.sojodia.extension

import com.numero.sojodia.model.Time
import junit.framework.TestCase
import org.junit.Test

class TimeExtensionTest {
    @Test
    fun countTime() {
        Time(10, 30).createCountTime(Time(0, 0)).apply {
            TestCase.assertEquals(hour, 10)
            TestCase.assertEquals(min, 30)
        }

        Time(8, 25).createCountTime(Time(8, 10)).apply {
            TestCase.assertEquals(hour, 0)
            TestCase.assertEquals(min, 15)
        }

        Time(12, 25).createCountTime(Time(11, 10)).apply {
            TestCase.assertEquals(hour, 1)
            TestCase.assertEquals(min, 15)
        }

        Time(12, 25).createCountTime(Time(11, 10)).apply {
            TestCase.assertEquals(hour, 1)
            TestCase.assertEquals(min, 15)
        }
    }
}