package com.numero.sojodia.service

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import com.numero.sojodia.IApplication
import com.numero.sojodia.manager.NotificationManager
import com.numero.sojodia.repository.IBusDataRepository
import com.numero.sojodia.repository.IConfigRepository
import com.numero.sojodia.util.BroadCastUtil
import io.reactivex.disposables.Disposable

class UpdateBusDataService : IntentService(UpdateBusDataService::class.java.simpleName) {

    private lateinit var notificationManager: NotificationManager

    private val busDataRepository: IBusDataRepository
        get() = (application as IApplication).busDataRepository
    private val configRepository: IConfigRepository
        get() = (application as IApplication).configRepository

    private var disposable: Disposable? = null

    override fun onCreate() {
        super.onCreate()

        notificationManager = NotificationManager(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.apply {
            if (isDisposed.not()) {
                dispose()
            }
        }
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
        disposable = busDataRepository.loadBusDataConfig()
                .subscribe({
                    configRepository.apply {
                        masterVersionCode = it.version
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
        disposable = busDataRepository.loadAndSaveBusData().subscribe({
        }, {
            it.printStackTrace()
            stopSelf()
        }, {
            configRepository.versionCode = configRepository.masterVersionCode
            BroadCastUtil.sendBroadCast(this, BroadCastUtil.ACTION_FINISH_DOWNLOAD)
            stopSelf()
        })
    }
}
