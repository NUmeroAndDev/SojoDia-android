package com.numero.sojodia.repository

import com.numero.sojodia.resource.BusRoute
import com.numero.sojodia.resource.IBusDataSource
import com.numero.sojodia.resource.datasource.BusTime
import com.numero.sojodia.resource.datasource.api.BusDataResponse
import com.numero.sojodia.resource.model.Config
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
        val tkGoingList = mutableListOf<BusTime>()
        val tkReturnList = mutableListOf<BusTime>()
        val tndGoingList = mutableListOf<BusTime>()
        val tndReturnList = mutableListOf<BusTime>()

        list.asSequence().forEach {
            when (BusRoute.from(it.routeId)) {
                BusRoute.KUTC_TO_TK -> {
                    tkReturnList.add(it)
                }
                BusRoute.KUTC_TO_TND -> {
                    tndReturnList.add(it)
                }
                BusRoute.TK_TO_KUTC -> {
                    tkGoingList.add(it)
                }
                BusRoute.TND_TO_KUTC -> {
                    tndGoingList.add(it)
                }
            }
        }
        tkBusTimeListGoing = tkGoingList
        tkBusTimeListReturn = tkReturnList
        tndBusTimeListGoing = tndGoingList
        tndBusTimeListReturn = tndReturnList
    }
}
