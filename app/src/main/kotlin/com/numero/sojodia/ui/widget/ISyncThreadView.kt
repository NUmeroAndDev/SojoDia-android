package com.numero.sojodia.ui.widget

interface ISyncThreadView {

    var isSynced: Boolean

    fun doOnThread()
}