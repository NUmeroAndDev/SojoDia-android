package com.numero.sojodia.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.IBinder

import com.numero.sojodia.SojoDiaApplication
import com.numero.sojodia.api.BusDataApi
import com.numero.sojodia.di.ApplicationComponent
import com.numero.sojodia.manager.NotificationManager
import com.numero.sojodia.model.BusDataFile
import com.numero.sojodia.repository.ConfigRepository
import com.numero.sojodia.util.BroadCastUtil

import javax.inject.Inject

import io.reactivex.Observable

class UpdateBusDataService : IntentService(UpdateBusDataService::class.java.simpleName) {

    private val busDataFiles = BusDataFile.values()
    private lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var busDataApi: BusDataApi
    @Inject
    lateinit var configRepository: ConfigRepository

    private val component: ApplicationComponent
        get() = (application as SojoDiaApplication).component

    override fun onCreate() {
        super.onCreate()
        component.inject(this)

        notificationManager = NotificationManager(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationManager.cancelNotification()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onHandleIntent(intent: Intent?) {
        if (configRepository.isTodayUpdateChecked) {
            stopSelf()
            return
        }

        checkUpdate()
    }

    private fun checkUpdate() {
        busDataApi.getBusDataVersion()
                .subscribe({
                    configRepository.apply {
                        versionCode = it.toLong()
                        if (canUpdate()) {
                            executeUpdate()
                        }
                    }
                },{
                    it.printStackTrace()
                    stopSelf()
                })
    }

    private fun executeUpdate() {
        notificationManager.showNotification()
        Observable.fromArray(*busDataFiles)
                .flatMap { busDataFile ->
                    busDataApi.getBusData(busDataFile)
                            .doOnNext {saveDownLoadData(busDataFile.fileName, it)}
                }
                .subscribe({ }, {
                    it.printStackTrace()
                    stopSelf()
                }, {
                    configRepository.successUpdate()
                    BroadCastUtil.sendBroadCast(this, BroadCastUtil.ACTION_FINISH_DOWNLOAD)
                    stopSelf()
                })
    }

    @Throws(Exception::class)
    private fun saveDownLoadData(fileName: String, data: String) {
        openFileOutput(fileName, Context.MODE_PRIVATE).apply {
            write(data.toByteArray())
        }.close()
    }
}
