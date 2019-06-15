package com.numero.sojodia.core

interface View<in T> {

    fun setPresenter(presenter: T)

}
