package com.numero.sojodia.resource

import com.numero.sojodia.resource.datasource.BusTime
import com.numero.sojodia.resource.model.BusDataResponse
import com.numero.sojodia.resource.model.Config
import com.numero.sojodia.resource.model.Route
import io.reactivex.Maybe
import io.reactivex.Observable

interface IBusDataSource {

    fun getConfigObservable(): Observable<Config>

    fun loadBusDataObservable(): Observable<BusDataResponse>

    fun loadAllBusTimeObservable(): Maybe<List<BusTime>>

    fun loadBusTimeObservable(route: Route): Observable<List<BusTime>>

    fun getAndSaveBusData(): Observable<BusDataResponse>
}