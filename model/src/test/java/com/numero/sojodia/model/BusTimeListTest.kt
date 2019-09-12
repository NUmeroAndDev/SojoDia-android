package com.numero.sojodia.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BusTimeListTest {

    private lateinit var busTimeList: BusTimeList

    @Before
    fun setUp() {
        busTimeList = TkBusTimeList(
                listOf(
                        BusTime(
                                Time(10, 0),
                                Week.WEEKDAY,
                                isNonstop = false,
                                isOnlyOnSchooldays = false
                        ),
                        BusTime(
                                Time(12, 30),
                                Week.WEEKDAY,
                                isNonstop = false,
                                isOnlyOnSchooldays = false
                        ),
                        BusTime(
                                Time(14, 15),
                                Week.WEEKDAY,
                                isNonstop = false,
                                isOnlyOnSchooldays = false
                        )
                )
        )
    }

    @Test
    fun isEmpty() {
        assertFalse(busTimeList.isEmpty())
        assertTrue(TkBusTimeList.emptyList().isEmpty())
        assertTrue(TndBusTimeList.emptyList().isEmpty())
    }

    @Test
    fun getSize() {
        assertEquals(busTimeList.size, 3)
    }

    @Test
    fun findNearBusTimePosition() {
        val position_9_59 = busTimeList.findNearBusTimePosition(Time(hour = 9, min = 59))
        assertEquals(position_9_59, 0)

        val position_12_30 = busTimeList.findNearBusTimePosition(Time(hour = 12, min = 30))
        assertEquals(position_12_30, 2)

        val position_17_00 = busTimeList.findNearBusTimePosition(Time(hour = 17, min = 0))
        assertEquals(position_17_00, BusTimeList.NO_BUS_POSITION)
    }
}