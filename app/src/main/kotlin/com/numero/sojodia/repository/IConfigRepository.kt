package com.numero.sojodia.repository


interface IConfigRepository {
    var versionCode: Long

    var masterVersionCode: Long

    val isTodayUpdateChecked: Boolean

    val canUpdate: Boolean
}
