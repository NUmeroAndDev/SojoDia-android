package com.numero.sojodia.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.numero.sojodia.IApplication

val AppCompatActivity.app: IApplication
    get() = application as IApplication

fun AppCompatActivity.setNightMode(isNight: Boolean) {
    if (isNight) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}