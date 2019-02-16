package com.numero.sojodia.repository

import com.numero.sojodia.resource.BusRouteId
import com.numero.sojodia.resource.IBusDataSource
import com.numero.sojodia.resource.datasource.db.BusTimeData
import com.numero.sojodia.resource.datasource.api.BusDataResponse
import com.numero.sojodia.resource.model.Config
import io.reactivex.Maybe
import io.reactivex.Observable
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test

class BusDataRepositoryTest {

    private val kutcToTkDataList: List<BusDataResponse.BusTime> = listOf(
            BusDataResponse.BusTime(0, 0, 0, false, false)
    )
    private val kutcToTndDataList: List<BusDataResponse.BusTime> = listOf(
            BusDataResponse.BusTime(0, 0, 0, false, false)
    )
    private val tkToKutcDataList: List<BusDataResponse.BusTime> = listOf(
            BusDataResponse.BusTime(0, 0, 0, false, false)
    )
    private val tndToKutcDataList: List<BusDataResponse.BusTime> = listOf(
            BusDataResponse.BusTime(0, 0, 0, false, false)
    )

    private lateinit var busDataRepository: BusDataRepository
    private val busDataSource = MockBusDataSource(kutcToTkDataList, kutcToTndDataList, tkToKutcDataList, tndToKutcDataList)


    @Before
    fun setup() {
        busDataRepository = BusDataRepository(busDataSource)
    }

    @Test
    fun `getTkBusTimeListGoing_リストが返ってくること`() {
        assertTrue(busDataRepository.tkBusTimeListGoing.isNotEmpty())
    }

    @Test
    fun `getTkBusTimeListReturn_リストが返ってくること`() {
        assertTrue(busDataRepository.tkBusTimeListReturn.isNotEmpty())
    }

    @Test
    fun `getTndBusTimeListGoing_リストが返ってくること`() {
        assertTrue(busDataRepository.tndBusTimeListGoing.isNotEmpty())
    }

    @Test
    fun `getTndBusTimeListReturn_リストが返ってくること`() {
        assertTrue(busDataRepository.tndBusTimeListReturn.isNotEmpty())
    }

    @Test
    fun `loadBusDataConfig_設定データが返ってくること`() {
        val config = busDataRepository.loadBusDataConfig().test().values()[0]
        assertEquals(config, busDataSource.config)
    }

    @Test
    fun `loadAndSaveBusData_時刻データが返ってくること`() {
        val busData = busDataRepository.loadAndSaveBusData().test().values()[0]
        assertEquals(busData, busDataSource.busData)
    }

    @Test
    fun `reloadBusData_データが更新されること`() {
        assertFalse(busDataRepository.tkBusTimeListGoing.isEmpty())
        assertFalse(busDataRepository.tkBusTimeListReturn.isEmpty())
        assertFalse(busDataRepository.tndBusTimeListGoing.isEmpty())
        assertFalse(busDataRepository.tndBusTimeListReturn.isEmpty())

        busDataSource.updateBusData()

        busDataRepository.reloadBusData()
        assertTrue(busDataRepository.tkBusTimeListGoing.isEmpty())
        assertTrue(busDataRepository.tkBusTimeListReturn.isEmpty())
        assertTrue(busDataRepository.tndBusTimeListGoing.isEmpty())
        assertTrue(busDataRepository.tndBusTimeListReturn.isEmpty())
    }

    class MockBusDataSource(
            kutcToTkDataList: List<BusDataResponse.BusTime>,
            kutcToTndDataList: List<BusDataResponse.BusTime>,
            tkToKutcDataList: List<BusDataResponse.BusTime>,
            tndToKutcDataList: List<BusDataResponse.BusTime>
    ) : IBusDataSource {

        private var busTime = listOf(
                BusTimeData(routeId = BusRouteId.KUTC_TO_TK_ID, hour = 0, minute = 0, weekId = 0, isNonstop = false, isOnlyOnSchooldays = false),
                BusTimeData(routeId = BusRouteId.KUTC_TO_TND_ID, hour = 0, minute = 0, weekId = 0, isNonstop = false, isOnlyOnSchooldays = false),
                BusTimeData(routeId = BusRouteId.TK_TO_KUTC_ID, hour = 0, minute = 0, weekId = 0, isNonstop = false, isOnlyOnSchooldays = false),
                BusTimeData(routeId = BusRouteId.TND_TO_KUTC_ID, hour = 0, minute = 0, weekId = 0, isNonstop = false, isOnlyOnSchooldays = false)
        )


        val config = Config(20180101)
        var busData = BusDataResponse(kutcToTkDataList, kutcToTndDataList, tkToKutcDataList, tndToKutcDataList)
            private set

        override fun getConfigObservable(): Observable<Config> = Observable.just(config)

        override fun loadAllBusTime(): Maybe<List<BusTimeData>> = Maybe.just(busTime)

        override fun getAndSaveBusData(): Observable<BusDataResponse> = Observable.just(busData)

        fun updateBusData() {
            busTime = listOf()
        }
    }
}