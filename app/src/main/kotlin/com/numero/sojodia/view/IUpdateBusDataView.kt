package com.numero.sojodia.view

interface IUpdateBusDataView {
    fun successUpdate()

    fun noNeedUpdate()

    fun onError(throwable: Throwable)
}