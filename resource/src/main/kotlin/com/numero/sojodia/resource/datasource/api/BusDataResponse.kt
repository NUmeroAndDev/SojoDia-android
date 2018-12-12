package com.numero.sojodia.resource.datasource.api

import com.numero.sojodia.resource.model.Time
import com.numero.sojodia.resource.model.Week
import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class BusDataResponse(
        @Json(name = "KutcToTk")
        val kutcToTkDataList: List<BusTime>,
        @Json(name = "KutcToTnd")
        val kutcToTndDataList: List<BusTime>,
        @Json(name = "TkToKutc")
        val tkToKutcDataList: List<BusTime>,
        @Json(name = "TndToKutc")
        val tndToKutcDataList: List<BusTime>) {

    @JsonSerializable
    data class BusTime(
            val hour: Int,
            val minute: Int,
            @Json(name = "week")
            val weekId: Int,
            val isNonstop: Boolean,
            val isOnlyOnSchooldays: Boolean) {

        val time: Time = Time(hour, minute)

        val week: Week? = Week.find(weekId)
    }
}