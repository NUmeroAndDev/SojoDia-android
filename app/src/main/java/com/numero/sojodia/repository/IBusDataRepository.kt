package com.numero.sojodia.repository

import com.numero.sojodia.model.BusDataFile
import com.numero.sojodia.model.BusTime

import io.reactivex.Observable

interface IBusDataRepository {

    fun loadBusData(busDataFile: BusDataFile): Observable<List<BusTime>>

}
