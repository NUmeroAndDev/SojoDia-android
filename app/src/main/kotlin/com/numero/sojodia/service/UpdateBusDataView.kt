package com.numero.sojodia.service

interface UpdateBusDataView {
    fun successUpdate()

    fun noNeedUpdate()

    fun onError(throwable: Throwable)
}