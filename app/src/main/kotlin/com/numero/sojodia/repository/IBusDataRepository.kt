package com.numero.sojodia.repository

import com.numero.sojodia.model.BusDataFile
import com.numero.sojodia.model.BusTime
import io.reactivex.Observable

interface IBusDataRepository {

    var tkBusTimeListGoing: MutableList<BusTime>

    var tkBusTimeListReturn: MutableList<BusTime>

    var tndBusTimeListGoing: MutableList<BusTime>

    var tndBusTimeListReturn: MutableList<BusTime>

    fun loadAndSaveBusData(busDataFile: BusDataFile): Observable<String>

    fun clearCache()

}
