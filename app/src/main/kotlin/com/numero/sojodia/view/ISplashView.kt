package com.numero.sojodia.view

interface ISplashView {
    fun finishDownload()

    fun onError(throwable: Throwable)
}