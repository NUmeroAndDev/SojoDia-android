package com.numero.sojodia.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.numero.sojodia.Module
import com.numero.sojodia.model.AppTheme

val AppCompatActivity.module: Module
    get() = application as Module

fun AppCompatActivity.applyAppTheme(appTheme: AppTheme) {
    val mode = when (appTheme) {
        AppTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        AppTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
        AppTheme.SYSTEM_DEFAULT -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        AppTheme.AUTO_BATTERY -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
    }
    AppCompatDelegate.setDefaultNightMode(mode)
}