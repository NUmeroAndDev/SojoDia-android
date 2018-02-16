package com.numero.sojodia.repository


interface IConfigRepository {
    var versionCode: Long

    val isTodayUpdateChecked: Boolean

    val canUpdate: Boolean

    fun successUpdate()
}
