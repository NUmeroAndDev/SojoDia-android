package com.numero.sojodia.model

import com.numero.sojodia.extension.countTime
import com.numero.sojodia.extension.isOverTime
import org.junit.Assert.*
import org.junit.Test

class TimeTest {

    /**
     * 秒に関しては使用していないため、保証しない
     */
    @Test
    fun countTime() {
        Time(10, 30, 0).apply {
            countTime(Time(0, 0, 0))

            assertEquals(hour, 10)
            assertEquals(min, 30)
        }

        Time(8, 25, 0).apply {
            countTime(Time(8, 10, 0))

            assertEquals(hour, 0)
            assertEquals(min, 15)
        }

        Time(12, 25, 0).apply {
            countTime(Time(11, 10, 0))

            assertEquals(hour, 1)
            assertEquals(min, 15)
        }

        Time(12, 25, 0).apply {
            countTime(Time(11, 10, 0))

            assertEquals(hour, 1)
            assertEquals(min, 15)
        }
    }

    /**
     * 超えていれば true になる
     */
    @Test
    fun overTime() {
        Time(10, 30, 0).apply {
            assertFalse(isOverTime(Time(10, 29, 0)))
            assertFalse(isOverTime(Time(10, 30, 0)))
            assertTrue(isOverTime(Time(10, 31, 0)))
        }

        Time(23, 59, 0).apply {
            assertFalse(isOverTime(Time(23, 58, 0)))
            assertFalse(isOverTime(Time(0, 0, 0)))
        }
    }

}