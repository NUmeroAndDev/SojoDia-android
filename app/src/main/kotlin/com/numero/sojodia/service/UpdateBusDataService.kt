package com.numero.sojodia.service

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import com.numero.sojodia.Module
import com.numero.sojodia.util.BroadCastUtil

class UpdateBusDataService : IntentService(UpdateBusDataService::class.java.simpleName), UpdateBusDataView {

    private lateinit var presenter: UpdateBusDataPresenter

    override fun onCreate() {
        super.onCreate()

        val configRepository = (application as Module).configRepository
        val busDataRepository = (application as Module).busDataRepository

        presenter = UpdateBusDataPresenterImpl(this, configRepository, busDataRepository)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unSubscribe()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onHandleIntent(intent: Intent?) {
        presenter.checkUpdate()
    }

    override fun successUpdate() {
        BroadCastUtil.sendBroadCast(this, BroadCastUtil.ACTION_FINISH_DOWNLOAD)
        stopSelf()
    }

    override fun noNeedUpdate() {
        stopSelf()
    }

    override fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        stopSelf()
    }
}
