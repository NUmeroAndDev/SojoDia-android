package com.numero.sojodia.resource


import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import junit.framework.TestCase.assertEquals
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
        busDataSource = BusDataSource(context, "", api)
    }

    @Test
    fun loadConfigObservable() {
        val response = MockResponse()
        response.setBody("{\n" +
                "  \"version\" : 20180331\n" +
                "}")
        server.enqueue(response)

        val configResponse = busDataSource.loadConfigObservable().test().await().assertNoErrors().values()[0]
        assertEquals(configResponse.version, 20180331)
    }

    @Test
    fun createFileLoadObservable() {
    }

    @Test
    fun loadAndSaveBusData() {
    }
}