package com.numero.sojodia.model

import com.numero.sojodia.resource.datasource.BusTime
import java.util.*

class TimeTableRow(val hour: Int) {

    private val onWeekdayBusTime: MutableList<BusTime> = mutableListOf()
    private val onSaturdayBusTime: MutableList<BusTime> = mutableListOf()
    private val onSundayBusTime: MutableList<BusTime> = mutableListOf()

    fun createOnWeekdayText(isNotSchoolTerm: Boolean): String {
        return onWeekdayBusTime.createBusMinTimeString(isNotSchoolTerm)
    }

    fun createOnSaturdayText(isNotSchoolTerm: Boolean): String {
        return onSaturdayBusTime.createBusMinTimeString(isNotSchoolTerm)
    }

    fun createOnSundayText(isNotSchoolTerm: Boolean): String {
        return onSundayBusTime.createBusMinTimeString(isNotSchoolTerm)
    }

    fun addBusTimeOnWeekday(busTime: BusTime) {
        onWeekdayBusTime.add(busTime)
    }

    fun addBusTimeOnSaturday(busTime: BusTime) {
        onSaturdayBusTime.add(busTime)
    }

    fun addBusTimeOnSunday(busTime: BusTime) {
        onSundayBusTime.add(busTime)
    }

    private fun List<BusTime>.createBusMinTimeString(isNotSchoolTerm: Boolean): String {
        return buildString {
            this@createBusMinTimeString.asSequence().forEach {
                if (isNotSchoolTerm.not() or it.isOnlyOnSchooldays.not()) {
                    if (it.isNonstop) {
                        append("★")
                    }
                    append(String.format(Locale.ENGLISH, "%02d ", it.time.min))
                }
            }
        }
    }
}
