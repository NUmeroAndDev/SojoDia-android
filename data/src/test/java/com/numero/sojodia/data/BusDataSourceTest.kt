package com.numero.sojodia.data

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.numero.sojodia.model.Result
import com.numero.sojodia.data.remote.BusDataApi
import com.numero.sojodia.data.remote.ResourceJsonAdapterFactory
import com.squareup.moshi.Moshi
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])  // FIXME とりあえずの対応
class BusDataSourceTest {

    private lateinit var busDataSource: BusDataSource
    private val server: MockWebServer = MockWebServer()

    @Before
    fun setup() {
        val api = Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(ResourceJsonAdapterFactory.INSTANCE).build()))
                .build()
                .create(BusDataApi::class.java)
        busDataSource = BusDataSourceImpl(api)
    }

    @Test
    fun `getConfig_設定ファイルをサーバから取得すること`() {
        val response = MockResponse()
        response.setBody("{\n" +
                "  \"version\" : 20180331\n" +
                "}")
        server.enqueue(response)

        val result = busDataSource.getConfig()
        assertTrue(result is Result.Success)

        val config = (result as Result.Success).value
        assertEquals(config.latestVersion.value, 20180431)
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

        val result = busDataSource.getBusData()
        assertTrue(result is Result.Success)

        val busData = (result as Result.Success).value
        assertTrue(busData.isNoBusData.not())


        assertEquals(busData.tkBusTimeListReturn.size, 4)
        assertEquals(busData.tndBusTimeListReturn.size, 2)
        assertEquals(busData.tkBusTimeListGoing.size, 1)
        assertEquals(busData.tndBusTimeListGoing.size, 1)
    }
}