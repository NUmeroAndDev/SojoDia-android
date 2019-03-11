package com.numero.sojodia.data

import com.numero.sojodia.model.BusData

interface CacheBusDataSource {
    fun putBusData(busData: BusData)

    fun getBusData(): BusData

    fun clearCache()
}