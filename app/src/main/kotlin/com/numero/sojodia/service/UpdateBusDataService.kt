package com.numero.sojodia.service

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import com.numero.sojodia.SojoDiaApplication
import com.numero.sojodia.api.BusDataApi
import com.numero.sojodia.di.ApplicationComponent
import com.numero.sojodia.manager.NotificationManager
import com.numero.sojodia.model.BusDataFile
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.ConfigRepository
import com.numero.sojodia.util.BroadCastUtil
import io.reactivex.Observable
import io.reactivex.functions.Function4
import javax.inject.Inject

class UpdateBusDataService : IntentService(UpdateBusDataService::class.java.simpleName) {

    private lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var busDataApi: BusDataApi
    @Inject
    lateinit var busDataRepository: BusDataRepository
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
                        if (canUpdate) {
                            executeUpdate()
                        }
                    }
                }, {
                    it.printStackTrace()
                    stopSelf()
                })
    }

    private fun executeUpdate() {
        notificationManager.showNotification()
        val stream = Observable.zip(
                busDataRepository.loadAndSaveBusData(BusDataFile.TK_TO_KUTC),
                busDataRepository.loadAndSaveBusData(BusDataFile.TND_TO_KUTC),
                busDataRepository.loadAndSaveBusData(BusDataFile.KUTC_TO_TK),
                busDataRepository.loadAndSaveBusData(BusDataFile.KUTC_TO_TND),
                Function4<String, String, String, String, String> { _, _, _, t4 -> t4 }
        )
        stream.subscribe({
        }, {
            it.printStackTrace()
            stopSelf()
        }, {
            configRepository.successUpdate()
            BroadCastUtil.sendBroadCast(this, BroadCastUtil.ACTION_FINISH_DOWNLOAD)
            stopSelf()
        })
    }
}
