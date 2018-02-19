package com.numero.sojodia.model

import com.numero.sojodia.extension.countTime
import com.numero.sojodia.extension.isOverTime
import org.junit.Assert.*
import org.junit.Test

class TimeTest {

    @Test
    fun countTime() {
        Time(10, 30).apply {
            countTime(Time(0, 0))

            assertEquals(hour, 10)
            assertEquals(min, 30)
        }

        Time(8, 25).apply {
            countTime(Time(8, 10))

            assertEquals(hour, 0)
            assertEquals(min, 15)
        }

        Time(12, 25).apply {
            countTime(Time(11, 10))

            assertEquals(hour, 1)
            assertEquals(min, 15)
        }

        Time(12, 25).apply {
            countTime(Time(11, 10))

            assertEquals(hour, 1)
            assertEquals(min, 15)
        }
    }

    @Test
    fun overTime() {
        Time(10, 30).apply {
            assertFalse(isOverTime(Time(10, 29)))
            assertFalse(isOverTime(Time(10, 30)))
            assertTrue(isOverTime(Time(10, 31)))
        }

        Time(23, 59).apply {
            assertFalse(isOverTime(Time(23, 58)))
            assertFalse(isOverTime(Time(0, 0)))
        }
    }

}