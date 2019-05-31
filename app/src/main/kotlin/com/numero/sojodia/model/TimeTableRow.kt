package com.numero.sojodia.model

import java.util.*

data class TimeTableRowList(
        val value: List<TimeTableRow>
) {

    companion object {
        fun mapToTimeTableRowList(busTimeList: List<BusTime>): TimeTableRowList {
            val firstHour = busTimeList.first().time.hour
            val lastHour = busTimeList.last().time.hour
            val busTimeGroup = busTimeList
                    .asSequence()
                    .groupBy { it.time.hour }
            val list = (firstHour..lastHour).map { hour ->
                TimeTableRow(hour).apply {
                    busTimeGroup[hour]?.forEach { busTime ->
                        when (busTime.week) {
                            Week.WEEKDAY -> addBusTimeOnWeekday(busTime)
                            Week.SATURDAY -> addBusTimeOnSaturday(busTime)
                            Week.SUNDAY -> addBusTimeOnSunday(busTime)
                        }
                    }
                }
            }
            return TimeTableRowList(list)
        }
    }
}

data class TimeTableRow(val hour: Int) {

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
