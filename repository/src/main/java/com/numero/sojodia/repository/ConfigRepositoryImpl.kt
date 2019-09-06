package com.numero.sojodia.repository

import android.content.Context
import android.content.SharedPreferences
import android.text.format.DateFormat
import androidx.core.content.edit
import com.numero.sojodia.model.AppTheme
import com.numero.sojodia.model.CurrentVersion
import com.numero.sojodia.model.LatestVersion
import java.util.*

class ConfigRepositoryImpl(context: Context) : ConfigRepository {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

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

    override var appTheme: AppTheme
        set(value) {
            sharedPreferences.edit {
                putString(KEY_APP_THEME, value.key)
            }
        }
        get() {
            val entry = sharedPreferences.getStringOrThrow(KEY_APP_THEME, AppTheme.SYSTEM_DEFAULT.key)
            return AppTheme.from(entry)
        }

    private fun Calendar.getTodayStringOnlyFigure(): String {
        return DateFormat.format("yyyyMMdd", this).toString()
    }

    private fun SharedPreferences.getStringOrThrow(key: String, default: String): String {
        return getString(key, default) ?: throw IllegalArgumentException()
    }

    companion object {

        const val DEFAULT_DATA_VERSION = 20170401L

        private const val PREFERENCES = "PREFERENCES"
        private const val VERSION_CODE = "FIRST_BOOT"
        private const val KEY_APP_THEME = "KEY_APP_THEME"
        private const val DATE = "DATE"
    }
}
