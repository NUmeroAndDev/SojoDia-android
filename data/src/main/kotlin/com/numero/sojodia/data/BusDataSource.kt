package com.numero.sojodia.data

import com.numero.sojodia.model.BusData
import com.numero.sojodia.model.Config
import com.numero.sojodia.model.Result

interface BusDataSource {
    fun getConfig(): Result<Config>

    fun getBusData(): Result<BusData>
}