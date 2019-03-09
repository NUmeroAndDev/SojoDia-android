package com.numero.sojodia.repository

import com.numero.sojodia.model.BusData
import com.numero.sojodia.model.Config
import com.numero.sojodia.model.Result

interface BusDataRepository {

    suspend fun fetchConfig(): Result<Config>

    suspend fun fetchBusData(): Result<BusData>

    fun getBusData(): BusData

    fun reloadBusData()
}