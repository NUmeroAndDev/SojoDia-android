package com.numero.sojodia.extension

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.numero.sojodia.model.AppTheme

fun AppTheme.applyApplication() {
    val mode = when (this) {
        AppTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        AppTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
        AppTheme.SYSTEM_DEFAULT -> if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
        } else {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
    }
    AppCompatDelegate.setDefaultNightMode(mode)
}