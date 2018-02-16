package com.numero.sojodia.extension

import android.content.DialogInterface
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.numero.sojodia.SojoDiaApplication
import com.numero.sojodia.di.ApplicationComponent

val AppCompatActivity.component: ApplicationComponent?
    get() = (application as? SojoDiaApplication)?.component

fun AppCompatActivity.showDialog(@StringRes message: Int, @StringRes positiveButton: Int, listener: (DialogInterface, Int) -> Unit) {
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setCancelable(false)
        setPositiveButton(positiveButton, listener)
    }.show()
}