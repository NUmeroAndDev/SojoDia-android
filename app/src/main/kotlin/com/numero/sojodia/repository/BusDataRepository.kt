package com.numero.sojodia.repository

import com.numero.sojodia.resource.IBusDataSource
import com.numero.sojodia.resource.model.BusDataResponse
import com.numero.sojodia.resource.model.BusTime
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

    init {
        reloadBusData()
    }

    override fun loadBusDataConfig(): Observable<Config> = busDataSource.getConfigObservable()

    override fun loadAndSaveBusData(): Observable<BusDataResponse> = busDataSource.getAndSaveBusData()

    override fun reloadBusData() {
        val busData = busDataSource.loadBusDataObservable().blockingFirst()
        tkBusTimeListGoing = busData.tkToKutcDataList
        tkBusTimeListReturn = busData.kutcToTkDataList
        tndBusTimeListGoing = busData.tndToKutcDataList
        tndBusTimeListReturn = busData.kutcToTndDataList
    }
}
