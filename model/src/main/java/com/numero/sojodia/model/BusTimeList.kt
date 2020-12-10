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

    fun findNearBusTimePosition(time: Time): Int {
        return value.indexOfFirst { it.time > time }
    }

    fun findNearBusTime(time: Time): BusTime? {
        return value.firstOrNull { it.time > time }
    }
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

data class BusTimeListPosition(
        private var position: Int = 0
) {

    val value: Int
        get() = position
    private var maxPosition: Int = Int.MAX_VALUE
    private var minPosition: Int = 0

    val isNoNextAndPrevious: Boolean
        get() = position == NO_BUS_POSITION

    fun set(position: Int) {
        this.position = position
    }

    fun setMaxPosition(position: Int) {
        maxPosition = position
    }

    fun setMinPosition(position: Int) {
        minPosition = position
    }

    fun next() {
        position++
    }

    fun canNext(): Boolean {
        if (position == NO_BUS_POSITION) return false
        return position < maxPosition
    }

    fun previous() {
        position--
    }

    fun canPrevious(): Boolean {
        if (position == NO_BUS_POSITION) return false
        return position > minPosition
    }

    companion object {
        const val NO_BUS_POSITION = -1
    }
}