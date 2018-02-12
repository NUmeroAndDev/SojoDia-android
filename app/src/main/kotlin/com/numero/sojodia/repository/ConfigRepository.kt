package com.numero.sojodia.repository

import android.content.Context
import android.content.SharedPreferences
import com.numero.sojodia.extension.getTodayStringOnlyFigure

import java.util.*

class ConfigRepository(context: Context) : IConfigRepository {

    private val sharedPreferences: SharedPreferences

    override var versionCode: Long = -1
        set(value) {
            field = value
            sharedPreferences.edit().apply {
                putString(DATE, Calendar.getInstance().getTodayStringOnlyFigure())
            }.apply()
        }
        get() = sharedPreferences.getLong(VERSION_CODE, 20170401L)

    override val isTodayUpdateChecked: Boolean
        get() {
            return Calendar.getInstance().getTodayStringOnlyFigure() == sharedPreferences.getString(DATE, "")
        }

    init {
        sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun successUpdate() {
        sharedPreferences.edit().apply {
            putLong(VERSION_CODE, versionCode)
        }.apply()
    }

    override fun canUpdate(): Boolean {
        return sharedPreferences.getLong(VERSION_CODE, 20170401L) < versionCode
    }

    companion object {
        private const val PREFERENCES = "PREFERENCES"
        private const val VERSION_CODE = "FIRST_BOOT"
        private const val DATE = "DATE"
    }
}
