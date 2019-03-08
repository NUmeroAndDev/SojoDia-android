package com.numero.sojodia.resource

import android.content.Context
import androidx.room.Room
import com.numero.sojodia.model.BusData
import com.numero.sojodia.model.BusTime
import com.numero.sojodia.model.Time
import com.numero.sojodia.model.Week
import com.numero.sojodia.resource.local.BusTimeData
import com.numero.sojodia.resource.local.BusTimeDatabase
import com.numero.sojodia.resource.local.IBusTimeDao

class CacheBusDataSourceImpl(
        context: Context
) : CacheBusDataSource {

    private var memoryCacheBusData: BusData? = null
    private val busTimeDatabaseDao: IBusTimeDao = Room.databaseBuilder(context, BusTimeDatabase::class.java, BUS_TIME_DB_FILE_NAME).allowMainThreadQueries().build().busTimeDao()

    override fun putBusData(busData: BusData) {
        busTimeDatabaseDao.clearTable()
        busData.mapAndSaveDB()
    }

    override fun getBusData(): BusData {
        val cache = memoryCacheBusData
        if (cache != null && cache.isNoBusData.not()) {
            return cache
        }

        val list = busTimeDatabaseDao.findAll()
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
        val busData = BusData(tkGoingList, tkReturnList, tndGoingList, tndReturnList)
        memoryCacheBusData = busData
        return busData
    }

    private fun BusData.mapAndSaveDB() {
        tkBusTimeListGoing.mapAndSaveDB(BusRouteId.from(BusRoute.TK_TO_KUTC))
        tkBusTimeListReturn.mapAndSaveDB(BusRouteId.from(BusRoute.KUTC_TO_TK))
        tndBusTimeListGoing.mapAndSaveDB(BusRouteId.from(BusRoute.TND_TO_KUTC))
        tndBusTimeListReturn.mapAndSaveDB(BusRouteId.from(BusRoute.KUTC_TO_TND))
    }

    private fun List<BusTime>.mapAndSaveDB(busRouteId: BusRouteId) {
        asSequence().map {
            BusTimeData(
                    routeId = busRouteId.value,
                    hour = it.time.hour,
                    minute = it.time.min,
                    weekId = it.week.id,
                    isNonstop = it.isNonstop,
                    isOnlyOnSchooldays = it.isOnlyOnSchooldays)
        }.forEach {
            busTimeDatabaseDao.create(it)
        }
    }

    private fun BusTimeData.toBusTime(): BusTime {
        return BusTime(
                Time(hour, minute),
                Week.from(weekId),
                isNonstop,
                isOnlyOnSchooldays
        )
    }

    private val Week.id:Int
    get() {
        return when (this) {
            Week.WEEKDAY -> 0
            Week.SATURDAY -> 1
            Week.SUNDAY -> 2
        }
    }

    companion object {
        private const val BUS_TIME_DB_FILE_NAME = "BusTimeData.db"
    }

}