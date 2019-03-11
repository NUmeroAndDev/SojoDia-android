package com.numero.sojodia.data.remote

import com.numero.sojodia.data.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RemoteConfig {
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
                        .add(ResourceJsonAdapterFactory.INSTANCE)
                        .build()))
                .build()
    }

    fun createBusDataApi(): BusDataApi {
        return createRetrofit().create(BusDataApi::class.java)
    }
}