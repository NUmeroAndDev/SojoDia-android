package com.numero.sojodia.resource

import com.numero.sojodia.model.*
import com.numero.sojodia.resource.remote.BusDataApi
import com.numero.sojodia.resource.remote.RemoteConfig
import com.numero.sojodia.resource.remote.executeSync
import com.numero.sojodia.resource.remote.response.BusDataResponse
import com.numero.sojodia.resource.remote.response.ConfigResponse

class BusDataSourceImpl : BusDataSource {

    private val busDataApi :BusDataApi

    constructor() {
        busDataApi = RemoteConfig.createBusDataApi()
    }

    constructor(busDataApi: BusDataApi) {
        this.busDataApi = busDataApi
    }

    override fun getConfig(): Result<Config> {
        val result = busDataApi.getConfig().executeSync()
        return when (result) {
            is Result.Success -> {
                val config = result.value.toConfig()
                Result.Success(config)
            }
            is Result.Error -> Result.Error(result.throwable)
        }
    }

    override fun getBusData(): Result<BusData> {
        val result = busDataApi.getBusData().executeSync()
        return when (result) {
            is Result.Success -> {
                val busData = result.value.toBusData()
                Result.Success(busData)
            }
            is Result.Error -> Result.Error(result.throwable)
        }
    }

    private fun BusDataResponse.toBusData(): BusData {
        val tkBusTimeListGoing = tkToKutcDataList.toBusTimeList()
        val tkBusTimeListReturn = kutcToTkDataList.toBusTimeList()
        val tndBusTimeListGoing = tndToKutcDataList.toBusTimeList()
        val tndBusTimeListReturn = kutcToTndDataList.toBusTimeList()
        return BusData(tkBusTimeListGoing, tkBusTimeListReturn, tndBusTimeListGoing, tndBusTimeListReturn)
    }

    private fun List<BusDataResponse.BusTime>.toBusTimeList(): List<BusTime> {
        return map {
            BusTime(
                    Time(it.hour, it.minute),
                    Week.from(it.weekId),
                    it.isNonstop,
                    it.isOnlyOnSchooldays
            )
        }
    }

    private fun ConfigResponse.toConfig(): Config {
        return Config(LatestVersion(version))
    }
}