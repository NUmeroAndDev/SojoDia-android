package com.numero.sojodia.api

import com.numero.sojodia.api.response.BusDataResponse
import com.numero.sojodia.model.Config
import io.reactivex.Observable
import retrofit2.http.GET

interface BusDataApi {

    @GET("/NUmeroAndDev/SojoDia-BusDate/v2/Config.json")
    fun getConfig(): Observable<Config>

    @GET("/NUmeroAndDev/SojoDia-BusDate/v2/BusData.json")
    fun getBusData(): Observable<BusDataResponse>
}