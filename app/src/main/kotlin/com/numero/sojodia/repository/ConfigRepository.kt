package com.numero.sojodia.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.numero.sojodia.extension.getTodayStringOnlyFigure
import com.numero.sojodia.model.AppTheme
import com.numero.sojodia.model.CurrentVersion
import com.numero.sojodia.model.LatestVersion
import java.util.*

class ConfigRepository(context: Context) : IConfigRepository {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    private val settingsPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override val currentVersion: CurrentVersion
        get() {
            val version = sharedPreferences.getLong(VERSION_CODE, DEFAULT_DATA_VERSION)
            return CurrentVersion(version)
        }

    override val isTodayUpdateChecked: Boolean
        get() {
            return Calendar.getInstance().getTodayStringOnlyFigure() == sharedPreferences.getString(DATE, "")
        }

    override fun updateBusDataVersion(latestVersion: LatestVersion) {
        sharedPreferences.edit {
            putLong(VERSION_CODE, latestVersion.value)
        }
    }

    override fun updateCheckUpdateDate() {
        sharedPreferences.edit {
            putString(DATE, Calendar.getInstance().getTodayStringOnlyFigure())
        }
    }

    override val appTheme: AppTheme
        get() {
            val entry = settingsPreferences.getString("select_app_theme", "0")
            return AppTheme.from(entry)
        }

    companion object {

        const val DEFAULT_DATA_VERSION = 20170401L

        private const val PREFERENCES = "PREFERENCES"
        private const val VERSION_CODE = "FIRST_BOOT"
        private const val DATE = "DATE"
    }
}
