package com.numero.sojodia.resource.datasource.api

import com.numero.sojodia.resource.datasource.api.response.BusDataResponse
import com.numero.sojodia.resource.datasource.api.response.ConfigResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface BusDataApi {

    @GET("/NUmeroAndDev/SojoDia-BusDate/v2/Config.json")
    fun getConfig(): Observable<ConfigResponse>

    @GET("/NUmeroAndDev/SojoDia-BusDate/v2/BusData.json")
    fun getBusData(): Observable<BusDataResponse>
}