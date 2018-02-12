package com.numero.sojodia.extension

import android.support.v7.app.AppCompatActivity
import com.numero.sojodia.SojoDiaApplication
import com.numero.sojodia.di.ApplicationComponent

fun AppCompatActivity.getApplicationComponent(): ApplicationComponent? {
    return (application as? SojoDiaApplication)?.component
}