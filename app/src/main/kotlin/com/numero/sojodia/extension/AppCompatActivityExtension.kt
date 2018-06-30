package com.numero.sojodia.extension

import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.numero.sojodia.IApplication

val AppCompatActivity.app: IApplication
    get() = application as IApplication

fun AppCompatActivity.showDialog(@StringRes message: Int, @StringRes positiveButton: Int, listener: (DialogInterface, Int) -> Unit) {
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setCancelable(false)
        setPositiveButton(positiveButton, listener)
    }.show()
}