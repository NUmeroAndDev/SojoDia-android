package com.numero.sojodia.resource

import android.content.Context
import androidx.room.Room
import com.numero.sojodia.resource.datasource.BusTimeDatabase
import com.numero.sojodia.resource.model.BusDataResponse
import com.numero.sojodia.resource.model.Config
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import java.io.File

class BusDataSource(
        private val context: Context,
        baseUrl: String,
        private val busDataApi: BusDataApi = ResourceConfig.createBusDataApi(baseUrl)
) : IBusDataSource {

    private val busTimeDatabaseRoom = Room.databaseBuilder(context, BusTimeDatabase::class.java, "BusTime.db").build()

    private val moshi = Moshi.Builder().add(ResourceJsonAdapterFactory.INSTANCE).build()

    override fun getConfigObservable(): Observable<Config> = busDataApi.getConfig()

    override fun loadBusDataObservable(): Observable<BusDataResponse> {
        return Observable.create<String> {
            // ダウンロードしたファイルがなければアセットから取得
            val file = File("%s/%s".format(context.filesDir.toString(), BUS_DATA_FILE_NAME))
            val source = if (file.exists()) {
                file.readText(Charsets.UTF_8)
            } else {
                context.readAssetsFile(BUS_DATA_FILE_NAME)
            }
            it.onNext(source)
        }.map {
            moshi.adapter(BusDataResponse::class.java).fromJson(it)
        }
    }

    override fun getAndSaveBusData(): Observable<BusDataResponse> {
        return busDataApi.getBusData().doOnNext {
            val value = moshi.adapter(BusDataResponse::class.java).toJson(it)
            saveDownLoadData(BUS_DATA_FILE_NAME, value)
        }
    }

    @Throws(Exception::class)
    private fun saveDownLoadData(fileName: String, data: String) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).apply {
            write(data.toByteArray())
        }.close()
    }

    private fun Context.readAssetsFile(fileName: String): String {
        return assets.open(fileName).reader(charset = Charsets.UTF_8).use { it.readText() }
    }

    companion object {
        private const val BUS_DATA_FILE_NAME: String = "BusData.json"
    }
}