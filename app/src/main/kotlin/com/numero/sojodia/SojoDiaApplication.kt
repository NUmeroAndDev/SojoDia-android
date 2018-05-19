package com.numero.sojodia

import android.app.Application
import com.numero.sojodia.api.ApplicationJsonAdapterFactory
import com.numero.sojodia.api.BusDataApi
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.ConfigRepository
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class SojoDiaApplication : Application(), IApplication {

    override val configRepository: ConfigRepository by lazy { ConfigRepository(this) }

    override val busDataRepository: BusDataRepository by lazy {
        val busDataApi = retrofit.create(BusDataApi::class.java)
        BusDataRepository(this, busDataApi)
    }

    private val retrofit: Retrofit = createRetrofit()

    private fun createRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
        }.build()
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BUS_DATA_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                        .add(ApplicationJsonAdapterFactory.INSTANCE)
                        .build()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
    }
}
