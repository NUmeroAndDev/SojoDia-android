package com.numero.sojodia.repository

import com.numero.sojodia.resource.IBusDataSource
import com.numero.sojodia.resource.datasource.BusTime
import com.numero.sojodia.resource.datasource.api.BusDataResponse
import com.numero.sojodia.resource.model.Config
import com.numero.sojodia.resource.model.Route
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

    override fun loadBusDataConfig(): Observable<Config> = busDataSource.getConfigObservable()

    override fun loadAndSaveBusData(): Observable<BusDataResponse> = busDataSource.getAndSaveBusData()

    override fun reloadBusData() {
        val list = busDataSource.loadAllBusTime().blockingGet()
        tkBusTimeListGoing = list.asSequence().filter { it.routeId == Route.TkToKutc.id }.toList()
        tkBusTimeListReturn = list.asSequence().filter { it.routeId == Route.KutcToTk.id }.toList()
        tndBusTimeListGoing = list.asSequence().filter { it.routeId == Route.TndToKutc.id }.toList()
        tndBusTimeListReturn = list.asSequence().filter { it.routeId == Route.KutcToTnd.id }.toList()
    }
}
