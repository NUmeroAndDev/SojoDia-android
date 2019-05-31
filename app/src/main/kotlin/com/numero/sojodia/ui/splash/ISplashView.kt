package com.numero.sojodia.ui.splash

interface ISplashView {

    fun successDownloadedBusData()

    fun onError(throwable: Throwable)
}