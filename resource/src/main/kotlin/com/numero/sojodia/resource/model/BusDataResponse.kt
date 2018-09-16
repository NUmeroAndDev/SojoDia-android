package com.numero.sojodia.resource.model

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
        val tndToKutcDataList: List<BusTime>
)