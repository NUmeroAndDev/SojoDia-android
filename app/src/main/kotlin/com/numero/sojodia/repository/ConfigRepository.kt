package com.numero.sojodia.repository

import android.content.Context
import android.content.SharedPreferences
import com.numero.sojodia.extension.getTodayStringOnlyFigure

import java.util.*

class ConfigRepository(context: Context) : IConfigRepository {

    private val sharedPreferences: SharedPreferences

    override var versionCode: Long
        set(value) {
            sharedPreferences.edit().apply {
                putLong(VERSION_CODE, value)
            }.apply()
        }
        get() = sharedPreferences.getLong(VERSION_CODE, 20170401L)

    override var masterVersionCode: Long = -1
        set(value) {
            field = value
            sharedPreferences.edit().apply {
                putString(DATE, Calendar.getInstance().getTodayStringOnlyFigure())
            }.apply()
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
