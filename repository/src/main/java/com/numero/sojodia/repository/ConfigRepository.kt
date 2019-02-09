package com.numero.sojodia.repository

import android.content.Context
import android.content.SharedPreferences
import android.text.format.DateFormat
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import java.util.*

class ConfigRepository(context: Context) : IConfigRepository {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    private val settingsPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override var versionCode: Long
        set(value) {
            sharedPreferences.edit {
                putLong(VERSION_CODE, value)
            }
        }
        get() = sharedPreferences.getLong(VERSION_CODE, DEFAULT_DATA_VERSION)

    override val isTodayUpdateChecked: Boolean
        get() {
            return Calendar.getInstance().getTodayStringOnlyFigure() == sharedPreferences.getString(DATE, "")
        }

    override fun updateCheckUpdateDate() {
        sharedPreferences.edit {
            putString(DATE, Calendar.getInstance().getTodayStringOnlyFigure())
        }
    }

    override val isUseDarkTheme: Boolean
        get() = settingsPreferences.getBoolean(KEY_USE_DARK_THEME, isDefaultUseDarkTheme)


    override val theme: Theme
        get() = if (isUseDarkTheme) {
            Theme.DARK
        } else {
            Theme.LIGHT
        }

    private fun Calendar.getTodayStringOnlyFigure(): String {
        return DateFormat.format("yyyyMMdd", this).toString()
    }

    companion object {

        const val DEFAULT_DATA_VERSION = 20170401L

        private const val PREFERENCES = "PREFERENCES"
        private const val VERSION_CODE = "FIRST_BOOT"
        private const val DATE = "DATE"
        private const val KEY_USE_DARK_THEME = "use_dark_theme"

        private const val isDefaultUseDarkTheme = false
    }
}
