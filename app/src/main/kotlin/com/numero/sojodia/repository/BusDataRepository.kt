package com.numero.sojodia.repository

import android.content.Context
import com.numero.sojodia.api.ApplicationJsonAdapterFactory
import com.numero.sojodia.api.BusDataApi
import com.numero.sojodia.api.response.BusDataResponse
import com.numero.sojodia.extension.readAssetsFile
import com.numero.sojodia.model.BusTime
import com.numero.sojodia.model.Config
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import java.io.File

class BusDataRepository(private val context: Context, private val busDataApi: BusDataApi) : IBusDataRepository {

    private val moshi = Moshi.Builder().add(ApplicationJsonAdapterFactory.INSTANCE).build()

    override var tkBusTimeListGoing: MutableList<BusTime> = mutableListOf()
        get() {
            if (field.isEmpty()) {
                initList()
            }
            return field
        }

    override var tkBusTimeListReturn: MutableList<BusTime> = mutableListOf()
        get() {
            if (field.isEmpty()) {
                initList()
            }
            return field
        }

    override var tndBusTimeListGoing: MutableList<BusTime> = mutableListOf()
        get() {
            if (field.isEmpty()) {
                initList()
            }
            return field
        }

    override var tndBusTimeListReturn: MutableList<BusTime> = mutableListOf()
        get() {
            if (field.isEmpty()) {
                initList()
            }
            return field
        }

    override fun loadBusDataConfig(): Observable<Config> = busDataApi.getConfig()

    override fun loadAndSaveBusData(): Observable<String> {
        return busDataApi.getBusData()
                .map { moshi.adapter(BusDataResponse::class.java).toJson(it) }
                .doOnNext({
                    saveDownLoadData(BUS_DATA_FILE_NAME, it)
                })
    }

    override fun clearCache() {
        tkBusTimeListGoing.clear()
        tkBusTimeListReturn.clear()
        tndBusTimeListGoing.clear()
        tndBusTimeListReturn.clear()
    }

    @Throws(Exception::class)
    private fun saveDownLoadData(fileName: String, data: String) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).apply {
            write(data.toByteArray())
        }.close()
    }

    private fun initList() {
        createFileLoadObservable().subscribe({
            tkBusTimeListGoing = it.tkToKutcDataList.toMutableList()
            tkBusTimeListReturn = it.kutcToTkDataList.toMutableList()
            tndBusTimeListGoing = it.tndToKutcDataList.toMutableList()
            tndBusTimeListReturn = it.kutcToTndDataList.toMutableList()
        })
    }

    private fun createFileLoadObservable(): Observable<BusDataResponse> {
        return Observable.create<String> {
            // ダウンロードしたファイルがなければアセットから取得
            val file = File("%s/%s".format(context.filesDir.toString(), BUS_DATA_FILE_NAME))
            val source = if (file.exists()) {
                file.readText(Charsets.UTF_8)
            } else {
                context.readAssetsFile(BUS_DATA_FILE_NAME)
            }
            it.onNext(source)
        }
                .map { moshi.adapter(BusDataResponse::class.java).fromJson(it) }
    }

    companion object {
        private const val BUS_DATA_FILE_NAME: String = "BusData.json"
    }
}
