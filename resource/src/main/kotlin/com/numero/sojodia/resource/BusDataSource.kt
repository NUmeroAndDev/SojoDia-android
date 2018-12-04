package com.numero.sojodia.resource

import android.content.Context
import androidx.room.Room
import com.numero.sojodia.resource.datasource.BusTime
import com.numero.sojodia.resource.datasource.api.ApiConfig
import com.numero.sojodia.resource.datasource.api.BusDataApi
import com.numero.sojodia.resource.datasource.api.BusDataResponse
import com.numero.sojodia.resource.datasource.db.BusTimeDatabase
import com.numero.sojodia.resource.datasource.db.IBusTimeDao
import com.numero.sojodia.resource.model.Config
import com.numero.sojodia.resource.model.Route
import io.reactivex.Maybe
import io.reactivex.Observable

class BusDataSource(
        private val context: Context,
        baseUrl: String,
        private val busDataApi: BusDataApi = ApiConfig.createBusDataApi(baseUrl),
        private val busTimeDatabaseDao: IBusTimeDao = Room.databaseBuilder(context, BusTimeDatabase::class.java, BUS_TIME_DB_FILE_NAME).allowMainThreadQueries().build().busTimeDao()
) : IBusDataSource {

    override val existBusTimeDB: Boolean
        get() {
            return context.getDatabasePath(BUS_TIME_DB_FILE_NAME).exists()
        }

    override fun getConfigObservable(): Observable<Config> = busDataApi.getConfig()

    override fun loadAllBusTime(): Maybe<List<BusTime>> {
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
            busDataResponse.kutcToTkDataList.mapAndSaveDB(Route.KutcToTk)
            busDataResponse.kutcToTndDataList.mapAndSaveDB(Route.KutcToTnd)
            busDataResponse.tkToKutcDataList.mapAndSaveDB(Route.TkToKutc)
            busDataResponse.tndToKutcDataList.mapAndSaveDB(Route.TndToKutc)

            e.onNext(busDataResponse)
        }
    }

    private fun List<BusDataResponse.BusTime>.mapAndSaveDB(route: Route) {
        asSequence().map {
            BusTime(
                    routeId = route.id,
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
        private const val BUS_TIME_DB_FILE_NAME = "BusTime.db"
    }
}