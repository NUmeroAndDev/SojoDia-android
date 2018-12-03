package com.numero.sojodia.resource

import android.content.Context
import androidx.room.Room
import com.numero.sojodia.resource.datasource.BusTime
import com.numero.sojodia.resource.datasource.api.ApiConfig
import com.numero.sojodia.resource.datasource.api.BusDataApi
import com.numero.sojodia.resource.datasource.db.BusTimeDatabase
import com.numero.sojodia.resource.datasource.api.BusDataResponse
import com.numero.sojodia.resource.datasource.api.ResourceJsonAdapterFactory
import com.numero.sojodia.resource.model.Config
import com.numero.sojodia.resource.model.Route
import com.squareup.moshi.Moshi
import io.reactivex.Maybe
import io.reactivex.Observable
import java.io.File

class BusDataSource(
        private val context: Context,
        baseUrl: String,
        private val busDataApi: BusDataApi = ApiConfig.createBusDataApi(baseUrl)
) : IBusDataSource {

    private val busTimeDatabaseDao = Room.databaseBuilder(context, BusTimeDatabase::class.java, "BusTime.db").allowMainThreadQueries().build().busTimeDao()

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

    override fun loadAllBusTimeObservable(): Maybe<List<BusTime>> {
        return busTimeDatabaseDao.findAll()
    }

    override fun loadBusTimeObservable(route: Route): Observable<List<BusTime>> {
        return busTimeDatabaseDao.find(route.id).toObservable()
    }

    override fun getAndSaveBusData(): Observable<BusDataResponse> {
        return busDataApi.getBusData()
                .flatMap {
                    saveBusDataObservable(it)
                }
                .doOnNext {
                    val value = moshi.adapter(BusDataResponse::class.java).toJson(it)
                    saveDownLoadData(BUS_DATA_FILE_NAME, value)
                }
    }

    private fun saveBusDataObservable(busDataResponse: BusDataResponse): Observable<BusDataResponse> {
        return Observable.create { e ->
            busDataResponse.kutcToTkDataList.asSequence().map {
                BusTime(
                        routeId = Route.KutcToTk.id,
                        hour = it.hour,
                        minute = it.minute,
                        weekId = it.weekId,
                        isNonstop = it.isNonstop,
                        isOnlyOnSchooldays = it.isOnlyOnSchooldays)
            }.forEach {
                busTimeDatabaseDao.create(it)
            }

            busDataResponse.kutcToTndDataList.asSequence().map {
                BusTime(
                        routeId = Route.KutcToTnd.id,
                        hour = it.hour,
                        minute = it.minute,
                        weekId = it.weekId,
                        isNonstop = it.isNonstop,
                        isOnlyOnSchooldays = it.isOnlyOnSchooldays)
            }.forEach {
                busTimeDatabaseDao.create(it)
            }

            busDataResponse.tkToKutcDataList.asSequence().map {
                BusTime(
                        routeId = Route.TkToKutc.id,
                        hour = it.hour,
                        minute = it.minute,
                        weekId = it.weekId,
                        isNonstop = it.isNonstop,
                        isOnlyOnSchooldays = it.isOnlyOnSchooldays)
            }.forEach {
                busTimeDatabaseDao.create(it)
            }

            busDataResponse.tndToKutcDataList.asSequence().map {
                BusTime(
                        routeId = Route.TndToKutc.id,
                        hour = it.hour,
                        minute = it.minute,
                        weekId = it.weekId,
                        isNonstop = it.isNonstop,
                        isOnlyOnSchooldays = it.isOnlyOnSchooldays)
            }.forEach {
                busTimeDatabaseDao.create(it)
            }

            e.onNext(busDataResponse)
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