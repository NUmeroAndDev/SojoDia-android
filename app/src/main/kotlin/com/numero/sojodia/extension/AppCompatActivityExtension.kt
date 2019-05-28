package com.numero.sojodia.extension

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.numero.sojodia.IApplication
import com.numero.sojodia.model.AppTheme

val AppCompatActivity.app: IApplication
    get() = application as IApplication

fun AppCompatActivity.applyAppTheme(appTheme: AppTheme) {
    val mode = when (appTheme) {
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