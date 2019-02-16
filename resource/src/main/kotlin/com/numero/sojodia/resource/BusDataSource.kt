package com.numero.sojodia.resource

import android.content.Context
import androidx.room.Room
import com.numero.sojodia.resource.datasource.db.BusTimeData
import com.numero.sojodia.resource.datasource.api.ApiConfig
import com.numero.sojodia.resource.datasource.api.BusDataApi
import com.numero.sojodia.resource.datasource.api.BusDataResponse
import com.numero.sojodia.resource.datasource.db.BusTimeDatabase
import com.numero.sojodia.resource.datasource.db.IBusTimeDao
import com.numero.sojodia.resource.model.Config
import io.reactivex.Maybe
import io.reactivex.Observable

class BusDataSource(
        private val context: Context,
        private val busDataApi: BusDataApi = ApiConfig.createBusDataApi(),
        private val busTimeDatabaseDao: IBusTimeDao = Room.databaseBuilder(context, BusTimeDatabase::class.java, BUS_TIME_DB_FILE_NAME).allowMainThreadQueries().build().busTimeDao()
) : IBusDataSource {

    override fun getConfigObservable(): Observable<Config> = busDataApi.getConfig()

    override fun loadAllBusTime(): Maybe<List<BusTimeData>> {
        return busTimeDatabaseDao.findAll()
    }

    override fun getAndSaveBusData(): Observable<BusDataResponse> {
        return busDataApi.getBusData()
                .flatMap {
                    saveBusDataObservable(it)
                }
    }

    private fun saveBusDataObservable(busDataResponse: BusDataResponse): Observable<BusDataResponse> {
        return Observable.create { e ->
            busTimeDatabaseDao.clearTable()
            busDataResponse.kutcToTkDataList.mapAndSaveDB(BusRouteId.from(BusRoute.KUTC_TO_TK))
            busDataResponse.kutcToTndDataList.mapAndSaveDB(BusRouteId.from(BusRoute.KUTC_TO_TND))
            busDataResponse.tkToKutcDataList.mapAndSaveDB(BusRouteId.from(BusRoute.TK_TO_KUTC))
            busDataResponse.tndToKutcDataList.mapAndSaveDB(BusRouteId.from(BusRoute.TND_TO_KUTC))

            e.onNext(busDataResponse)
        }
    }

    private fun List<BusDataResponse.BusTime>.mapAndSaveDB(busRouteId: BusRouteId) {
        asSequence().map {
            BusTimeData(
                    routeId = busRouteId.value,
                    hour = it.hour,
                    minute = it.minute,
                    weekId = it.weekId,
                    isNonstop = it.isNonstop,
                    isOnlyOnSchooldays = it.isOnlyOnSchooldays)
        }.forEach {
            busTimeDatabaseDao.create(it)
        }
    }

    companion object {
        private const val BUS_TIME_DB_FILE_NAME = "BusTimeData.db"
    }
}