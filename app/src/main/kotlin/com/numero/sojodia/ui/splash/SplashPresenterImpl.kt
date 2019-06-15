package com.numero.sojodia.ui.splash

import com.numero.sojodia.model.Result
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.IConfigRepository
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class SplashPresenterImpl(
        private val view: SplashView,
        private val busDataRepository: BusDataRepository,
        private val configRepository: IConfigRepository
) : SplashPresenter {

    private val job = Job()
    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun subscribe() {
        executeLoadBusData()
    }

    override fun unSubscribe() {
        job.cancelChildren()
    }

    private fun executeLoadBusData() {
        CoroutineScope(coroutineContext).launch(Dispatchers.Main) {
            val configAsync = async(Dispatchers.IO) { busDataRepository.fetchConfig() }
            val busDataAsync = async(Dispatchers.IO) { busDataRepository.fetchBusData() }
            val splashDelayAsync = async { delay(SPLASH_TIME) }
            val config = configAsync.await()
            val busData = busDataAsync.await()
            splashDelayAsync.await()

            if (config is Result.Success && busData is Result.Success) {
                configRepository.updateBusDataVersion(config.value.latestVersion)
                configRepository.updateCheckUpdateDate()
                view.successDownloadedBusData()
            } else {
                view.onError(Exception())
            }
        }
    }

    companion object {
        private val SPLASH_TIME: Long = TimeUnit.SECONDS.toMillis(1)
    }
}