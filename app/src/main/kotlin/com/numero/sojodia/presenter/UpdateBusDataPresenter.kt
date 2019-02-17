package com.numero.sojodia.presenter

import com.numero.sojodia.model.LatestVersion
import com.numero.sojodia.repository.IBusDataRepository
import com.numero.sojodia.repository.IConfigRepository
import com.numero.sojodia.view.IUpdateBusDataView
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.blockingSubscribeBy

class UpdateBusDataPresenter(
        private val view: IUpdateBusDataView,
        private val configRepository: IConfigRepository,
        private val busDataRepository: IBusDataRepository
) : IUpdateBusDataPresenter {

    private var disposable: Disposable? = null

    override fun subscribe() {
    }

    override fun unSubscribe() {
        disposable?.run {
            if (isDisposed.not()) {
                dispose()
            }
        }
    }

    override fun checkUpdate() {
        if (configRepository.isTodayUpdateChecked) {
            view.noNeedUpdate()
            return
        }
        busDataRepository.loadBusDataConfig().blockingSubscribeBy(
                onNext = {
                    configRepository.updateCheckUpdateDate()
                    if (it.checkUpdate(configRepository.currentVersion)) {
                        executeUpdate(it.latestVersion)
                    } else {
                        view.noNeedUpdate()
                    }
                },
                onError = {
                    view.onError(it)
                }
        )
    }

    private fun executeUpdate(latestVersion: LatestVersion) {
        busDataRepository.loadAndSaveBusData().blockingSubscribeBy(
                onNext = {
                    configRepository.updateBusDataVersion(latestVersion)
                    view.successUpdate()
                },
                onError = {
                    view.onError(it)
                }
        )
    }
}