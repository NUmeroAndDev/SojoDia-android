package com.numero.sojodia.repository

import com.numero.sojodia.resource.datasource.BusTime
import com.numero.sojodia.resource.datasource.api.BusDataResponse
import com.numero.sojodia.resource.model.Config
import io.reactivex.Observable

interface IBusDataRepository {

    val tkBusTimeListGoing: List<BusTime>

    val tkBusTimeListReturn: List<BusTime>

    val tndBusTimeListGoing: List<BusTime>

    val tndBusTimeListReturn: List<BusTime>

    val isNoBusTimeData: Boolean

    fun loadBusDataConfig(): Observable<Config>

    fun loadAndSaveBusData(): Observable<BusDataResponse>

    fun reloadBusData()

}
