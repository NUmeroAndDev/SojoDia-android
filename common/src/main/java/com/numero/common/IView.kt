package com.numero.common

interface IView<in T> {

    fun setPresenter(presenter: T)

}
