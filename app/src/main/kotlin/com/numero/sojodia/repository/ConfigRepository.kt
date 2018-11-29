package com.numero.sojodia.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.numero.sojodia.R
import com.numero.sojodia.extension.getTodayStringOnlyFigure
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


    override val themeRes: Int
        get() = if (isUseDarkTheme) {
            R.style.AppTheme_Dark_NoActionBar
        } else {
            R.style.AppTheme_Light_NoActionBar
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
