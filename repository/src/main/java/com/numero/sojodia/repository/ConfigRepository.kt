package com.numero.sojodia.repository

import com.numero.sojodia.model.AppTheme
import com.numero.sojodia.model.CurrentVersion
import com.numero.sojodia.model.LatestVersion

interface ConfigRepository {

    val currentVersion: CurrentVersion

    val isTodayUpdateChecked: Boolean

    var appTheme: AppTheme

    fun updateBusDataVersion(latestVersion: LatestVersion)

    fun updateCheckUpdateDate()
}
