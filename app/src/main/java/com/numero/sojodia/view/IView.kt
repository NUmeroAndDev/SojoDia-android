package com.numero.sojodia.view

interface IView<in T> {

    fun setPresenter(presenter: T)

}
