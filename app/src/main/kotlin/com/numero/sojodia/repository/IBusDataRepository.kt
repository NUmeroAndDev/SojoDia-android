package com.numero.sojodia.repository

import com.numero.sojodia.resource.model.BusTime
import com.numero.sojodia.resource.model.Config
import io.reactivex.Observable

interface IBusDataRepository {

    var tkBusTimeListGoing: MutableList<BusTime>

    var tkBusTimeListReturn: MutableList<BusTime>

    var tndBusTimeListGoing: MutableList<BusTime>

    var tndBusTimeListReturn: MutableList<BusTime>

    fun loadBusDataConfig(): Observable<Config>

    fun loadAndSaveBusData(): Observable<String>

    fun clearCache()

}
