package com.numero.sojodia.repository

import com.numero.sojodia.resource.BusDataSource
import com.numero.sojodia.resource.model.BusDataResponse
import com.numero.sojodia.resource.model.BusTime
import com.numero.sojodia.resource.model.Config
import io.reactivex.Observable

class BusDataRepository(
        private val busDataSource: BusDataSource
) : IBusDataRepository {

    override var tkBusTimeListGoing: MutableList<BusTime> = mutableListOf()
        get() {
            if (field.isEmpty()) {
                initList()
            }
            return field
        }

    override var tkBusTimeListReturn: MutableList<BusTime> = mutableListOf()
        get() {
            if (field.isEmpty()) {
                initList()
            }
            return field
        }

    override var tndBusTimeListGoing: MutableList<BusTime> = mutableListOf()
        get() {
            if (field.isEmpty()) {
                initList()
            }
            return field
        }

    override var tndBusTimeListReturn: MutableList<BusTime> = mutableListOf()
        get() {
            if (field.isEmpty()) {
                initList()
            }
            return field
        }

    override fun loadBusDataConfig(): Observable<Config> = busDataSource.loadConfigObservable()

    override fun loadAndSaveBusData(): Observable<BusDataResponse> {
        return busDataSource.loadAndSaveBusData()
    }

    override fun clearCache() {
        tkBusTimeListGoing.clear()
        tkBusTimeListReturn.clear()
        tndBusTimeListGoing.clear()
        tndBusTimeListReturn.clear()
    }

    private fun initList() {
        busDataSource.createFileLoadObservable().subscribe({
            tkBusTimeListGoing = it.tkToKutcDataList.toMutableList()
            tkBusTimeListReturn = it.kutcToTkDataList.toMutableList()
            tndBusTimeListGoing = it.tndToKutcDataList.toMutableList()
            tndBusTimeListReturn = it.kutcToTndDataList.toMutableList()
        })
    }
}
