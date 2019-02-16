package com.numero.sojodia.repository

import com.numero.sojodia.model.BusTime
import com.numero.sojodia.model.Config
import com.numero.sojodia.model.Time
import com.numero.sojodia.model.Week
import com.numero.sojodia.resource.BusRoute
import com.numero.sojodia.resource.IBusDataSource
import com.numero.sojodia.resource.datasource.api.response.BusDataResponse
import com.numero.sojodia.resource.datasource.api.response.ConfigResponse
import com.numero.sojodia.resource.datasource.db.BusTimeData
import io.reactivex.Observable

class BusDataRepository(
        private val busDataSource: IBusDataSource
) : IBusDataRepository {

    override var tkBusTimeListGoing: List<BusTime> = listOf()
        private set

    override var tkBusTimeListReturn: List<BusTime> = listOf()
        private set

    override var tndBusTimeListGoing: List<BusTime> = listOf()
        private set

    override var tndBusTimeListReturn: List<BusTime> = listOf()
        private set

    override val isNoBusTimeData: Boolean
        get() {
            return tkBusTimeListGoing.isEmpty() and tkBusTimeListReturn.isEmpty() and tndBusTimeListGoing.isEmpty() and tndBusTimeListReturn.isEmpty()
        }

    init {
        reloadBusData()
    }

    override fun loadBusDataConfig(): Observable<Config> = busDataSource.getConfigObservable().map { it.toConfig() }

    override fun loadAndSaveBusData(): Observable<BusDataResponse> = busDataSource.getAndSaveBusData()

    override fun reloadBusData() {
        val list = busDataSource.loadAllBusTime().blockingGet()
        val tkGoingList = mutableListOf<BusTime>()
        val tkReturnList = mutableListOf<BusTime>()
        val tndGoingList = mutableListOf<BusTime>()
        val tndReturnList = mutableListOf<BusTime>()

        list.asSequence().forEach {
            val busTime = it.toBusTime()
            when (BusRoute.from(it.routeId)) {
                BusRoute.KUTC_TO_TK -> {
                    tkReturnList.add(busTime)
                }
                BusRoute.KUTC_TO_TND -> {
                    tndReturnList.add(busTime)
                }
                BusRoute.TK_TO_KUTC -> {
                    tkGoingList.add(busTime)
                }
                BusRoute.TND_TO_KUTC -> {
                    tndGoingList.add(busTime)
                }
            }
        }
        tkBusTimeListGoing = tkGoingList
        tkBusTimeListReturn = tkReturnList
        tndBusTimeListGoing = tndGoingList
        tndBusTimeListReturn = tndReturnList
    }

    private fun ConfigResponse.toConfig() :Config {
        return Config(version)
    }

    private fun BusTimeData.toBusTime(): BusTime {
        return BusTime(
                Time(hour, minute),
                Week.from(weekId),
                isNonstop,
                isOnlyOnSchooldays
        )
    }
}
