package com.numero.sojodia.resource.remote

import com.numero.sojodia.resource.remote.response.BusDataResponse
import com.numero.sojodia.resource.remote.response.ConfigResponse
import retrofit2.Call
import retrofit2.http.GET

interface BusDataApi {

    @GET("/NUmeroAndDev/SojoDia-BusDate/v2/Config.json")
    fun getConfig(): Call<ConfigResponse>

    @GET("/NUmeroAndDev/SojoDia-BusDate/v2/BusData.json")
    fun getBusData(): Call<BusDataResponse>
}