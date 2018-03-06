package com.numero.sojodia.api

import com.numero.sojodia.api.response.BusDataResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface BusDataApi2 {

    @GET("/BusData.json")
    fun getBusData(): Observable<BusDataResponse>
}