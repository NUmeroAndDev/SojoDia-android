package com.numero.sojodia.model

data class BusTime(
        val time: Time,
        val week: Week,
        val isNonstop: Boolean,
        val isOnlyOnSchooldays: Boolean
)

sealed class BusTimeList(val value: List<BusTime>) {

    fun isEmpty(): Boolean = value.isEmpty()

    val size: Int = value.size
}

class TkBusTimeList(
        value: List<BusTime>
) : BusTimeList(value) {

    companion object {
        fun emptyList(): TkBusTimeList = TkBusTimeList(listOf())
    }
}

class TndBusTimeList(
        value: List<BusTime>
) : BusTimeList(value) {

    companion object {
        fun emptyList(): TndBusTimeList = TndBusTimeList(listOf())
    }
}