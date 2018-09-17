package com.numero.sojodia.presenter

import com.numero.sojodia.repository.IBusDataRepository
import com.numero.sojodia.repository.IConfigRepository
import com.numero.sojodia.view.IUpdateBusDataView
import io.reactivex.disposables.Disposable

class UpdateBusDataPresenter(
        private val view: IUpdateBusDataView,
        private val configRepository: IConfigRepository,
        private val busDataRepository: IBusDataRepository) : IUpdateBusDataPresenter {

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
        disposable = busDataRepository.loadBusDataConfig()
                .subscribe({
                    configRepository.updateCheckUpdateDate()
                    if (configRepository.versionCode < it.version) {
                        executeUpdate(it.version)
                    } else {
                        view.noNeedUpdate()
                    }
                }, {
                    view.onError(it)
                })
    }


    private fun executeUpdate(latestVersion: Long) {
        // notificationManager.showNotification()
        disposable = busDataRepository.loadAndSaveBusData().subscribe({
        }, {
            view.onError(it)
        }, {
            configRepository.versionCode = latestVersion
            view.successUpdate()
        })
    }
}