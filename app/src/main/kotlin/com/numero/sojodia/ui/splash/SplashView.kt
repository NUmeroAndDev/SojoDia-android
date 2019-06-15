package com.numero.sojodia.ui.splash

interface SplashView {

    fun successDownloadedBusData()

    fun onError(throwable: Throwable)
}