package com.numero.sojodia.model

import com.numero.sojodia.extension.countTime
import org.junit.Assert.assertEquals
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

}