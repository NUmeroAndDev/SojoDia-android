package com.numero.sojodia.model

data class BusTime(
        val id: Long = 0,
        val routeId: Int,
        val hour: Int,
        val minute: Int,
        val weekId: Int,
        val isNonstop: Boolean,
        val isOnlyOnSchooldays: Boolean
) {

    val route: BusRoute = BusRoute.find(routeId)

    val week: Week? = Week.find(weekId)

    val time: Time = Time(hour, minute)
}