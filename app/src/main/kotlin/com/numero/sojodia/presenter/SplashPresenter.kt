package com.numero.sojodia.presenter

import com.numero.sojodia.view.ISplashView
import io.reactivex.disposables.Disposable

class SplashPresenter(
        private val view: ISplashView
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
        // TODO バージョンの読み込みと時刻データのダウンロード
    }
}