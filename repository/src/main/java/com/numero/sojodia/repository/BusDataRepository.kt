package com.numero.sojodia.repository

import com.numero.sojodia.model.BusTime
import com.numero.sojodia.model.Config
import com.numero.sojodia.resource.IBusDataSource
import com.numero.sojodia.resource.datasource.api.BusDataResponse
import com.numero.sojodia.resource.model.BusRoute
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

    override fun loadBusDataConfig(): Observable<Config> = busDataSource.getConfigObservable().map { Config(it.version) }

    override fun loadAndSaveBusData(): Observable<BusDataResponse> = busDataSource.getAndSaveBusData()

    override fun reloadBusData() {
        val list = busDataSource.loadAllBusTime().blockingGet()
        tkBusTimeListGoing = list.asSequence().filter { it.routeId == BusRoute.TkToKutc.id }.map { it.mapToBusTime() }.toList()
        tkBusTimeListReturn = list.asSequence().filter { it.routeId == BusRoute.KutcToTk.id }.map { it.mapToBusTime() }.toList()
        tndBusTimeListGoing = list.asSequence().filter { it.routeId == BusRoute.TndToKutc.id }.map { it.mapToBusTime() }.toList()
        tndBusTimeListReturn = list.asSequence().filter { it.routeId == BusRoute.KutcToTnd.id }.map { it.mapToBusTime() }.toList()
    }

    private fun com.numero.sojodia.resource.datasource.BusTime.mapToBusTime():BusTime {
        return BusTime(
                id, routeId, hour, minute, weekId, isNonstop, isOnlyOnSchooldays
        )
    }
}
