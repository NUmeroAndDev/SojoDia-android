package com.numero.sojodia.resource

import com.numero.sojodia.resource.datasource.db.BusTimeData
import com.numero.sojodia.resource.datasource.api.BusDataResponse
import com.numero.sojodia.resource.model.Config
import io.reactivex.Maybe
import io.reactivex.Observable

interface IBusDataSource {

    fun getConfigObservable(): Observable<Config>

    fun loadAllBusTime(): Maybe<List<BusTimeData>>

    fun getAndSaveBusData(): Observable<BusDataResponse>
}