package com.numero.sojodia.extension

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.numero.sojodia.R
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

fun AppTheme.getTitle(context: Context): String {
    val res = when (this) {
        AppTheme.LIGHT -> R.string.theme_light
        AppTheme.DARK -> R.string.theme_dark
        AppTheme.SYSTEM_DEFAULT -> if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            R.string.theme_auto_battery
        } else {
            R.string.theme_system_default
        }
    }
    return context.getString(res)
}