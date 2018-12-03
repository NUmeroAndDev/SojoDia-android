package com.numero.sojodia.resource


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.numero.sojodia.resource.datasource.BusTime
import com.numero.sojodia.resource.datasource.api.BusDataApi
import com.numero.sojodia.resource.datasource.api.ResourceJsonAdapterFactory
import com.numero.sojodia.resource.datasource.db.IBusTimeDao
import com.numero.sojodia.resource.model.Route
import com.squareup.moshi.Moshi
import io.reactivex.Flowable
import io.reactivex.Maybe
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(AndroidJUnit4::class)
class BusDataSourceTest {

    private lateinit var busDataSource: BusDataSource
    private val database = MockBusDataDao()
    private val server: MockWebServer = MockWebServer()

    @Before
    fun setup() {
        val api = Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(ResourceJsonAdapterFactory.INSTANCE).build()))
                .build()
                .create(BusDataApi::class.java)
        val context = InstrumentationRegistry.getInstrumentation().context
        busDataSource = BusDataSource(context, "", api, database)
    }

    @Test
    fun `getConfigObservable_設定ファイルをサーバから取得すること`() {
        val response = MockResponse()
        response.setBody("{\n" +
                "  \"version\" : 20180331\n" +
                "}")
        server.enqueue(response)

        val configResponse = busDataSource.getConfigObservable().test().await().assertNoErrors().values()[0]
        assertEquals(configResponse.version, 20180331)
    }

    @Test
    fun `loadAllBusTime_時刻表を読み込むこと`() {
        val list = busDataSource.loadAllBusTime().test().assertNoErrors().values()[0]
        assertTrue(list.isNotEmpty())
        assertEquals(list.size, 4)
    }

    @Test
    fun `getAndSaveBusData_時刻表をサーバから取得すること`() {
        val responseValue = "{\n" +
                "  \"KutcToTk\": [\n" +
                "    {\n" +
                "      \"hour\": 7,\n" +
                "      \"minute\": 4,\n" +
                "      \"week\": 0,\n" +
                "      \"isNonstop\": false,\n" +
                "      \"isOnlyOnSchooldays\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"hour\": 7,\n" +
                "      \"minute\": 33,\n" +
                "      \"week\": 0,\n" +
                "      \"isNonstop\": false,\n" +
                "      \"isOnlyOnSchooldays\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"hour\": 7,\n" +
                "      \"minute\": 49,\n" +
                "      \"week\": 1,\n" +
                "      \"isNonstop\": false,\n" +
                "      \"isOnlyOnSchooldays\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"hour\": 7,\n" +
                "      \"minute\": 59,\n" +
                "      \"week\": 2,\n" +
                "      \"isNonstop\": false,\n" +
                "      \"isOnlyOnSchooldays\": false\n" +
                "    }\n" +
                "  ],\n" +
                "  \"KutcToTnd\": [\n" +
                "    {\n" +
                "      \"hour\": 6,\n" +
                "      \"minute\": 35,\n" +
                "      \"week\": 0,\n" +
                "      \"isNonstop\": false,\n" +
                "      \"isOnlyOnSchooldays\": false\n" +
                "    },\n" +
                "    {\n" +
                "      \"hour\": 7,\n" +
                "      \"minute\": 1,\n" +
                "      \"week\": 0,\n" +
                "      \"isNonstop\": false,\n" +
                "      \"isOnlyOnSchooldays\": false\n" +
                "    }\n" +
                "  ],\n" +
                "  \"TkToKutc\": [\n" +
                "    {\n" +
                "      \"hour\": 6,\n" +
                "      \"minute\": 35,\n" +
                "      \"week\": 0,\n" +
                "      \"isNonstop\": false,\n" +
                "      \"isOnlyOnSchooldays\": false\n" +
                "    }\n" +
                "  ],\n" +
                "  \"TndToKutc\": [\n" +
                "    {\n" +
                "      \"hour\": 6,\n" +
                "      \"minute\": 20,\n" +
                "      \"week\": 0,\n" +
                "      \"isNonstop\": false,\n" +
                "      \"isOnlyOnSchooldays\": false\n" +
                "    }\n" +
                "  ]\n" +
                "}\n"
        val response = MockResponse()
        response.setBody(responseValue)
        server.enqueue(response)

        val busTimeResponse = busDataSource.getAndSaveBusData().test().assertNoErrors().values()[0]
        assertEquals(busTimeResponse.kutcToTkDataList.size, 4)
        assertEquals(busTimeResponse.kutcToTndDataList.size, 2)
        assertEquals(busTimeResponse.tkToKutcDataList.size, 1)
        assertEquals(busTimeResponse.tndToKutcDataList.size, 1)

        assertTrue(database.isInserted)
    }

    class MockBusDataDao : IBusTimeDao {

        var isInserted: Boolean = false
            private set

        override fun create(busTime: BusTime) {
            isInserted = true
        }

        override fun findAll(): Maybe<List<BusTime>> {
            val list = listOf(
                    BusTime(
                            routeId = Route.KutcToTk.id,
                            hour = 0,
                            minute = 0,
                            weekId = 0,
                            isNonstop = false,
                            isOnlyOnSchooldays = false
                    ),
                    BusTime(
                            routeId = Route.KutcToTnd.id,
                            hour = 0,
                            minute = 0,
                            weekId = 0,
                            isNonstop = false,
                            isOnlyOnSchooldays = false
                    ),
                    BusTime(
                            routeId = Route.TkToKutc.id,
                            hour = 0,
                            minute = 0,
                            weekId = 0,
                            isNonstop = false,
                            isOnlyOnSchooldays = false
                    ),
                    BusTime(
                            routeId = Route.TndToKutc.id,
                            hour = 0,
                            minute = 0,
                            weekId = 0,
                            isNonstop = false,
                            isOnlyOnSchooldays = false
                    )
            )
            return Maybe.just(list)
        }

        override fun find(routeId: Int): Flowable<List<BusTime>> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}