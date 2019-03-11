package com.numero.sojodia.repository

import android.content.Context
import com.numero.sojodia.model.BusData
import com.numero.sojodia.model.Config
import com.numero.sojodia.data.BusDataSource
import com.numero.sojodia.data.BusDataSourceImpl
import com.numero.sojodia.data.CacheBusDataSource
import com.numero.sojodia.data.CacheBusDataSourceImpl
import com.numero.sojodia.model.Result

class BusDataRepositoryImpl : BusDataRepository {

    private val cacheBusDataSource: CacheBusDataSource
    private val busDataSource: BusDataSource

    constructor(context: Context) {
        cacheBusDataSource = CacheBusDataSourceImpl(context)
        busDataSource = BusDataSourceImpl()
    }

    constructor(cacheBusDataSource: CacheBusDataSource, busDataSource: BusDataSource) {
        this.cacheBusDataSource = cacheBusDataSource
        this.busDataSource = busDataSource
    }

    override suspend fun fetchConfig(): Result<Config> {
        return busDataSource.getConfig()
    }

    override suspend fun fetchBusData(): Result<BusData> {
        val result = busDataSource.getBusData()
        return when (result) {
            is Result.Success -> {
                cacheBusDataSource.putBusData(result.value)
                result
            }
            is Result.Error -> result
        }
    }

    override fun getBusData(): BusData {
        return cacheBusDataSource.getBusData()
    }

    override fun reloadBusData() {
        cacheBusDataSource.clearCache()
    }
}