package com.numero.sojodia.model

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class TimeTableRowListTest {

    @Before
    fun setUp() {
    }

    @Test
    fun `mapToTimeTableRowList_TimeTableRowListが取得できること`() {
        val busTimeList: List<BusTime> = listOf(
                BusTime(Time(6, 10), Week.WEEKDAY, false, false),
                BusTime(Time(6, 21), Week.WEEKDAY, false, false),
                BusTime(Time(7, 10), Week.WEEKDAY, false, false),
                BusTime(Time(20, 10), Week.WEEKDAY, false, false)
        )
        val rowList = TimeTableRowList.mapToTimeTableRowList(busTimeList)
        assertEquals(rowList.value.size, 15)
    }
}