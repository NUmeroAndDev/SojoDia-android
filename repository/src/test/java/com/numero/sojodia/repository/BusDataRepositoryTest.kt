package com.numero.sojodia.repository

import com.numero.sojodia.model.*
import com.numero.sojodia.data.BusDataSource
import com.numero.sojodia.data.CacheBusDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class BusDataRepositoryTest {

    private val config = Config(LatestVersion(20190101L))
    private val busData = BusData(
            listOf(BusTime(Time(0, 0), Week.WEEKDAY, isNonstop = false, isOnlyOnSchooldays = false)),
            listOf(BusTime(Time(0, 0), Week.WEEKDAY, isNonstop = false, isOnlyOnSchooldays = false)),
            listOf(BusTime(Time(0, 0), Week.WEEKDAY, isNonstop = false, isOnlyOnSchooldays = false)),
            listOf(BusTime(Time(0, 0), Week.WEEKDAY, isNonstop = false, isOnlyOnSchooldays = false))
    )

    private lateinit var repository: BusDataRepository
    private val cacheDataSource: MockCacheDataSource = MockCacheDataSource(busData)
    private val busDataSource: MockBusDataSource = MockBusDataSource(config, busData)

    @Before
    fun setUp() {
        repository = BusDataRepositoryImpl(cacheDataSource, busDataSource)
    }

    @Test
    fun `fetchConfig_Configが取得できること`() = runBlocking {
        val result = repository.fetchConfig()
        assertTrue(result is Result.Success)

        val config = (result as Result.Success).value
        assertEquals(config, this@BusDataRepositoryTest.config)
    }

    @Test
    fun `fetchBusData_BusDataが取得でき、ローカルに保存されること`() = runBlocking {
        val result = repository.fetchBusData()
        assertTrue(result is Result.Success)

        val busData = (result as Result.Success).value
        assertEquals(busData, this@BusDataRepositoryTest.busData)
        assertTrue(cacheDataSource.isSavedLocal)
    }

    @Test
    fun `getBusData_BusDataが取得できること`() {
        val busData = repository.getBusData()
        assertEquals(busData, this@BusDataRepositoryTest.busData)
    }

    class MockCacheDataSource(
            private val busData: BusData
    ) : CacheBusDataSource {

        var hasCache: Boolean = false
            private set
        var isSavedLocal: Boolean = false
            private set

        override fun putBusData(busData: BusData) {
            isSavedLocal = true
        }

        override fun getBusData(): BusData {
            hasCache = true
            return busData
        }

        override fun clearCache() {
            hasCache = false
        }
    }

    class MockBusDataSource(
            private val mockConfig: Config,
            private val mockBusData: BusData
    ) : BusDataSource {

        override fun getConfig(): Result<Config> = Result.Success(mockConfig)

        override fun getBusData(): Result<BusData> = Result.Success(mockBusData)

    }
}