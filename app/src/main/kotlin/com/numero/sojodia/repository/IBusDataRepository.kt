package com.numero.sojodia.repository

import com.numero.sojodia.resource.datasource.BusTime
import com.numero.sojodia.resource.model.BusDataResponse
import com.numero.sojodia.resource.model.Config
import io.reactivex.Observable

interface IBusDataRepository {

    val tkBusTimeListGoing: List<BusTime>

    val tkBusTimeListReturn: List<BusTime>

    val tndBusTimeListGoing: List<BusTime>

    val tndBusTimeListReturn: List<BusTime>

    fun loadBusDataConfig(): Observable<Config>

    fun loadAndSaveBusData(): Observable<BusDataResponse>

    fun reloadBusData()

}
