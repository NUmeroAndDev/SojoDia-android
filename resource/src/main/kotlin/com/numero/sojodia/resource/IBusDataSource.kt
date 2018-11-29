package com.numero.sojodia.resource

import com.numero.sojodia.resource.model.BusDataResponse
import com.numero.sojodia.resource.model.Config
import io.reactivex.Observable

interface IBusDataSource {

    fun getConfigObservable(): Observable<Config>

    fun loadBusDataObservable(): Observable<BusDataResponse>

    fun getAndSaveBusData(): Observable<BusDataResponse>
}