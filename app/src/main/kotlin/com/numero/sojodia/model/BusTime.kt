package com.numero.sojodia.model

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class BusTime(
        val hour: Int,
        val minute: Int,
        @Json(name = "week")
        val weekId: Int,
        val isNonstop: Boolean,
        val isOnlyOnSchooldays: Boolean) {

    val time: Time = Time(hour, minute)

    val week: Week? = Week.getWeek(weekId)
}
