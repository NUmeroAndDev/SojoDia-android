package com.numero.sojodia.model

import com.numero.sojodia.extension.createCountTime
import com.numero.sojodia.extension.isOverTime
import org.junit.Assert.*
import org.junit.Test

class TimeTest {

    @Test
    fun countTime() {
        Time(10, 30).createCountTime(Time(0, 0)).apply {
            assertEquals(hour, 10)
            assertEquals(min, 30)
        }

        Time(8, 25).createCountTime(Time(8, 10)).apply {
            assertEquals(hour, 0)
            assertEquals(min, 15)
        }

        Time(12, 25).createCountTime(Time(11, 10)).apply {
            assertEquals(hour, 1)
            assertEquals(min, 15)
        }

        Time(12, 25).createCountTime(Time(11, 10)).apply {
            assertEquals(hour, 1)
            assertEquals(min, 15)
        }
    }

    @Test
    fun overTime() {
        Time(10, 30).apply {
            assertFalse(Time(10, 29).isOverTime(this))
            assertFalse(Time(10, 30).isOverTime(this))
            assertTrue(Time(10, 31).isOverTime(this))
        }

        Time(23, 59).apply {
            assertFalse(Time(23, 58).isOverTime(this))
            assertFalse(Time(0, 0).isOverTime(this))
        }
    }

}