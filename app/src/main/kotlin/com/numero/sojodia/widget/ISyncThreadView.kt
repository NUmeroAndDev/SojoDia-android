package com.numero.sojodia.widget

interface ISyncThreadView {

    var isSynced: Boolean

    fun doOnThread()
}