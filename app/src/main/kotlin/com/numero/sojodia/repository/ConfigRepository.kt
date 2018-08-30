package com.numero.sojodia.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.numero.sojodia.extension.getTodayStringOnlyFigure

import java.util.*

class ConfigRepository(context: Context) : IConfigRepository {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    override var versionCode: Long
        set(value) {
            sharedPreferences.edit {
                putLong(VERSION_CODE, value)
            }
        }
        get() = sharedPreferences.getLong(VERSION_CODE, 20170401L)

    override val isTodayUpdateChecked: Boolean
        get() {
            return Calendar.getInstance().getTodayStringOnlyFigure() == sharedPreferences.getString(DATE, "")
        }

    override fun updateCheckUpdateDate() {
        sharedPreferences.edit {
            putString(DATE, Calendar.getInstance().getTodayStringOnlyFigure())
        }
    }

    companion object {
        private const val PREFERENCES = "PREFERENCES"
        private const val VERSION_CODE = "FIRST_BOOT"
        private const val DATE = "DATE"
    }
}
