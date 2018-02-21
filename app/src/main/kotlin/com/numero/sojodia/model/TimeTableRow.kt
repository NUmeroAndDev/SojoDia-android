package com.numero.sojodia.model

import java.util.*

class TimeTableRow(val hour: Int) {

    private val timeOnWeekdayStringBuilder = StringBuilder()
    private val timeOnSaturdayStringBuilder = StringBuilder()
    private val timeOnSundayStringBuilder = StringBuilder()

    val timeOnWeekdayText: String
        get() = timeOnWeekdayStringBuilder.toString()

    val timeOnSaturdayText: String
        get() = timeOnSaturdayStringBuilder.toString()

    val timeOnSundayText: String
        get() = timeOnSundayStringBuilder.toString()

    fun addBusTimeOnWeekday(busTime: BusTime) {
        if (busTime.isNonstop) {
            timeOnWeekdayStringBuilder.append("★")
        }
        timeOnWeekdayStringBuilder.append(String.format(Locale.ENGLISH, "%02d ", busTime.time.min))
    }

    fun addBusTimeOnSaturday(busTime: BusTime) {
        if (busTime.isNonstop) {
            timeOnSaturdayStringBuilder.append("★")
        }
        timeOnSaturdayStringBuilder.append(String.format(Locale.ENGLISH, "%02d ", busTime.time.min))
    }

    fun addBusTimeOnSunday(busTime: BusTime) {
        if (busTime.isNonstop) {
            timeOnSundayStringBuilder.append("★")
        }
        timeOnSundayStringBuilder.append(String.format(Locale.ENGLISH, "%02d ", busTime.time.min))
    }
}
