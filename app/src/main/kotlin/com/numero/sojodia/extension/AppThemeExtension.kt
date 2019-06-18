package com.numero.sojodia.extension

import androidx.appcompat.app.AppCompatDelegate
import com.numero.sojodia.model.AppTheme

fun AppTheme.applyApplication() {
    val mode = when (this) {
        AppTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        AppTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
        AppTheme.SYSTEM_DEFAULT -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        AppTheme.AUTO_BATTERY -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
    }
    AppCompatDelegate.setDefaultNightMode(mode)
}