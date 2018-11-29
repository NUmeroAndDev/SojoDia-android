package com.numero.sojodia.extension

import com.numero.sojodia.resource.model.Time
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

    @Test
    fun overTime() {
        Time(10, 30).apply {
            TestCase.assertFalse(Time(10, 29).isOverTime(this))
            TestCase.assertFalse(Time(10, 30).isOverTime(this))
            TestCase.assertTrue(Time(10, 31).isOverTime(this))
        }

        Time(23, 59).apply {
            TestCase.assertFalse(Time(23, 58).isOverTime(this))
            TestCase.assertFalse(Time(0, 0).isOverTime(this))
        }
    }
}