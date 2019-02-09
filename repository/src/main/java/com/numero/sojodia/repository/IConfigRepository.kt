package com.numero.sojodia.repository


interface IConfigRepository {
    var versionCode: Long

    val isTodayUpdateChecked: Boolean

    val isUseDarkTheme: Boolean

    val theme: Theme

    fun updateCheckUpdateDate()
}
