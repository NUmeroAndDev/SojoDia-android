package com.numero.sojodia.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TimeTest {

    private lateinit var time: Time

    @Before
    fun setUp() {
        time = Time(12, 12)
    }

    @Test
    fun `test_compareTo`() {
        assertEquals(time.compareTo(Time(12, 12)), 0)
        assertEquals(time.compareTo(Time(12, 13)), -1)
        assertEquals(time.compareTo(Time(13, 12)), -1)
        assertEquals(time.compareTo(Time(12, 11)), 1)
        assertEquals(time.compareTo(Time(11, 12)), 1)
        assertEquals(time.compareTo(Time(11, 14)), 1)

        assertTrue(time <= Time(12, 12))
        assertTrue(time >= Time(12, 12))
        assertTrue(time < Time(12, 13))
        assertTrue(time < Time(13, 12))
        assertTrue(time > Time(12, 11))
        assertTrue(time > Time(11, 12))
        assertTrue(time > Time(11, 14))
    }
}