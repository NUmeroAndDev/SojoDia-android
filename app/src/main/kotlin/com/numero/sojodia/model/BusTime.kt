package com.numero.sojodia.model

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class BusTime(
        val hour: Int,
        val minutes: Int,
        @Json(name = "week")
        val weekId: Int,
        val isNonstop: Boolean,
        val isOnlyOnSchooldays: Boolean) {

    val time: Time = Time(hour, minutes)

    val week: Week? = Week.getWeek(weekId)
}
