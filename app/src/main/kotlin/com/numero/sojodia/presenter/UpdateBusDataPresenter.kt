package com.numero.sojodia.presenter

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
                    if (configRepository.versionCode < it.version) {
                        executeUpdate(it.version)
                    } else {
                        view.noNeedUpdate()
                    }
                },
                onError = {
                    view.onError(it)
                }
        )
    }

    private fun executeUpdate(latestVersion: Long) {
        busDataRepository.loadAndSaveBusData().blockingSubscribeBy(
                onNext = {
                    configRepository.versionCode = latestVersion
                    view.successUpdate()
                },
                onError = {
                    view.onError(it)
                }
        )
    }
}