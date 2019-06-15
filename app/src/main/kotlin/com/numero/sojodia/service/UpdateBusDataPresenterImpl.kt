package com.numero.sojodia.service

import com.numero.sojodia.model.LatestVersion
import com.numero.sojodia.model.Result
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.ConfigRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class UpdateBusDataPresenterImpl(
        private val view: UpdateBusDataView,
        private val configRepository: ConfigRepository,
        private val busDataRepository: BusDataRepository
) : UpdateBusDataPresenter {

    override fun subscribe() {
    }

    override fun unSubscribe() {
    }

    override fun checkUpdate() {
        if (configRepository.isTodayUpdateChecked) {
            view.noNeedUpdate()
            return
        }
        runBlocking {
            val result = withContext(Dispatchers.Default) { busDataRepository.fetchConfig() }

            when(result) {
                is Result.Success -> {
                    val config = result.value
                    configRepository.updateCheckUpdateDate()
                    if (config.checkUpdate(configRepository.currentVersion)) {
                        executeUpdate(config.latestVersion)
                    } else {
                        view.noNeedUpdate()
                    }
                }
                is Result.Error -> {
                    view.onError(result.throwable)
                }
            }
        }
    }

    private fun executeUpdate(latestVersion: LatestVersion) {
        runBlocking {
            val result = withContext(Dispatchers.Default) { busDataRepository.fetchBusData() }

            when(result) {
                is Result.Success -> {
                    configRepository.updateBusDataVersion(latestVersion)
                    view.successUpdate()
                }
                is Result.Error -> {
                    view.onError(result.throwable)
                }
            }
        }
    }
}