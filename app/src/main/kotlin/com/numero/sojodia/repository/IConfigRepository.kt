package com.numero.sojodia.repository

import com.numero.sojodia.model.CurrentVersion
import com.numero.sojodia.model.LatestVersion

interface IConfigRepository {

    val currentVersion: CurrentVersion

    val isTodayUpdateChecked: Boolean

    val isUseDarkTheme: Boolean

    val themeRes: Int

    fun updateBusDataVersion(latestVersion: LatestVersion)

    fun updateCheckUpdateDate()
}
