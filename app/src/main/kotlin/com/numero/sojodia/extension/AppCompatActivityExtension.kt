package com.numero.sojodia.extension

import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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