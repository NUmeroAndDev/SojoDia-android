package com.numero.sojodia.resource

import com.numero.sojodia.resource.datasource.db.BusTimeData
import com.numero.sojodia.resource.datasource.api.response.BusDataResponse
import com.numero.sojodia.resource.datasource.api.response.ConfigResponse
import io.reactivex.Maybe
import io.reactivex.Observable

interface IBusDataSource {

    fun getConfigObservable(): Observable<ConfigResponse>

    fun loadAllBusTime(): Maybe<List<BusTimeData>>

    fun getAndSaveBusData(): Observable<BusDataResponse>
}