package com.numero.sojodia.component.splash

import com.numero.sojodia.model.Config
import com.numero.sojodia.repository.IBusDataRepository
import com.numero.sojodia.repository.IConfigRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

class SplashPresenter(
        private val view: ISplashView,
        private val busDataRepository: IBusDataRepository,
        private val configRepository: IConfigRepository
) : ISplashPresenter {

    private var disposable: Disposable? = null

    override fun subscribe() {
        executeLoadBusData()
    }

    override fun unSubscribe() {
        disposable?.run {
            if (isDisposed.not()) {
                dispose()
            }
        }
    }

    private fun executeLoadBusData() {
        disposable = splashObservables()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            configRepository.versionCode = it.version
                            configRepository.updateCheckUpdateDate()
                            busDataRepository.reloadBusData()
                            view.successDownloadedBusData()
                        },
                        onError = {
                            view.onError(it)
                        }
                )
    }

    private fun splashObservables(): Observable<Config> {
        return Observables.zip(
                busDataRepository.loadBusDataConfig(),
                busDataRepository.loadAndSaveBusData(),
                Observable.timer(SPLASH_TIME, TimeUnit.MILLISECONDS)
        ) { config, _, _ ->
            config
        }
    }

    companion object {
        private val SPLASH_TIME: Long = TimeUnit.SECONDS.toMillis(1)
    }
}