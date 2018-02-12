package com.numero.sojodia.api

import com.numero.sojodia.BuildConfig
import com.numero.sojodia.model.BusDataFile

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.Request

class BusDataApi(private val okHttpClient: OkHttpClient) {

    fun getBusDataVersion(): Observable<String> = createObservable(Request.Builder().url(BuildConfig.UPDATE_URL).build())

    fun getBusData(busDataFile: BusDataFile): Observable<String> = createObservable(Request.Builder().url(busDataFile.url).build())

    private fun createObservable(request: Request): Observable<String> {
        return Observable.create {
            val response = okHttpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                throw Exception(response.message())
            }
            val responseBody = response.body()
            if (responseBody != null) {
                it.onNext(responseBody.string())
                it.onComplete()
            } else {
                it.onError(Exception("Response data is null"))
            }
        }
    }
}
