package com.numero.sojodia.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.numero.sojodia.extension.getTodayStringOnlyFigure

import java.util.*

class ConfigRepository(context: Context) : IConfigRepository {

    private val sharedPreferences: SharedPreferences

    override var versionCode: Long
        set(value) {
            sharedPreferences.edit {
                putLong(VERSION_CODE, value)
            }
        }
        get() = sharedPreferences.getLong(VERSION_CODE, 20170401L)

    override var masterVersionCode: Long = -1
        set(value) {
            field = value
            sharedPreferences.edit {
                putString(DATE, Calendar.getInstance().getTodayStringOnlyFigure())
            }
        }

    override val isTodayUpdateChecked: Boolean
        get() {
            return Calendar.getInstance().getTodayStringOnlyFigure() == sharedPreferences.getString(DATE, "")
        }

    override val canUpdate: Boolean
        get() = versionCode < masterVersionCode

    init {
        sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    companion object {
        private const val PREFERENCES = "PREFERENCES"
        private const val VERSION_CODE = "FIRST_BOOT"
        private const val DATE = "DATE"
    }
}
