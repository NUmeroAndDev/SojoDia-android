package com.numero.sojodia.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BusTimeListPositionTest {

    private lateinit var position: BusTimeListPosition

    @Before
    fun setUp() {
        position = BusTimeListPosition()
    }

    @Test
    fun isNoNextAndPrevious() {
        position.set(BusTimeListPosition.NO_BUS_POSITION)
        assertTrue(position.isNoNextAndPrevious)

        position.set(1)
        assertFalse(position.isNoNextAndPrevious)
    }

    @Test
    fun next() {
        position.next()
        assertEquals(position.value, 1)

        position.next()
        assertEquals(position.value, 2)
    }

    @Test
    fun canNext() {
        position.setMaxPosition(5)
        position.set(2)
        assertTrue(position.canNext())

        position.set(4)
        assertTrue(position.canNext())

        position.set(5)
        assertFalse(position.canNext())
    }

    @Test
    fun previous() {
        position.set(5)
        position.previous()
        assertEquals(position.value, 4)

        position.previous()
        assertEquals(position.value, 3)
    }

    @Test
    fun canPrevious() {
        position.setMinPosition(0)
        position.set(5)
        assertTrue(position.canPrevious())

        position.set(1)
        assertTrue(position.canPrevious())

        position.set(0)
        assertFalse(position.canPrevious())
    }
}