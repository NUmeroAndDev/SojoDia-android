package com.numero.sojodia.repository

import com.numero.sojodia.resource.IBusDataSource
import com.numero.sojodia.resource.datasource.api.BusDataResponse
import com.numero.sojodia.resource.model.BusTime
import com.numero.sojodia.resource.model.Config
import io.reactivex.Observable
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test

class BusDataRepositoryTest {

    private val kutcToTkDataList: List<BusTime> = listOf(
            BusTime(0, 0, 0, false, false)
    )
    private val kutcToTndDataList: List<BusTime> = listOf(
            BusTime(0, 0, 0, false, false)
    )
    private val tkToKutcDataList: List<BusTime> = listOf(
            BusTime(0, 0, 0, false, false)
    )
    private val tndToKutcDataList: List<BusTime> = listOf(
            BusTime(0, 0, 0, false, false)
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
        assertEquals(busDataRepository.tkBusTimeListGoing, tkToKutcDataList)
    }

    @Test
    fun `getTkBusTimeListReturn_リストが返ってくること`() {
        assertTrue(busDataRepository.tkBusTimeListReturn.isNotEmpty())
        assertEquals(busDataRepository.tkBusTimeListReturn, kutcToTkDataList)
    }

    @Test
    fun `getTndBusTimeListGoing_リストが返ってくること`() {
        assertTrue(busDataRepository.tndBusTimeListGoing.isNotEmpty())
        assertEquals(busDataRepository.tndBusTimeListGoing, tndToKutcDataList)
    }

    @Test
    fun `getTndBusTimeListReturn_リストが返ってくること`() {
        assertTrue(busDataRepository.tndBusTimeListReturn.isNotEmpty())
        assertEquals(busDataRepository.tndBusTimeListReturn, kutcToTndDataList)
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
            kutcToTkDataList: List<BusTime>,
            kutcToTndDataList: List<BusTime>,
            tkToKutcDataList: List<BusTime>,
            tndToKutcDataList: List<BusTime>
    ) : IBusDataSource {

        val config = Config(20180101)
        var busData = BusDataResponse(kutcToTkDataList, kutcToTndDataList, tkToKutcDataList, tndToKutcDataList)
            private set

        override fun getConfigObservable(): Observable<Config> = Observable.just(config)

        override fun loadBusDataObservable(): Observable<BusDataResponse> = Observable.just(busData)

        override fun getAndSaveBusData(): Observable<BusDataResponse> = Observable.just(busData)

        fun updateBusData() {
            busData = BusDataResponse(listOf(), listOf(), listOf(), listOf())
        }
    }
}