package com.numero.sojodia.repository

import com.numero.sojodia.model.BusTime
import com.numero.sojodia.model.Config
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
