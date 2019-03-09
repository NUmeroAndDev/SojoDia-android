package com.numero.sojodia.resource

import com.numero.sojodia.model.BusData

interface CacheBusDataSource {
    fun putBusData(busData: BusData)

    fun getBusData(): BusData

    fun clearCache()
}