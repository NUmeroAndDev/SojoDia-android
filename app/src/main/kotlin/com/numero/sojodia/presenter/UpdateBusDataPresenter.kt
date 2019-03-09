package com.numero.sojodia.presenter

import com.numero.sojodia.model.LatestVersion
import com.numero.sojodia.model.Result
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.IConfigRepository
import com.numero.sojodia.view.IUpdateBusDataView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class UpdateBusDataPresenter(
        private val view: IUpdateBusDataView,
        private val configRepository: IConfigRepository,
        private val busDataRepository: BusDataRepository
) : IUpdateBusDataPresenter {

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