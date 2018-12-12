package com.numero.sojodia.extension

import androidx.appcompat.app.AppCompatActivity
import com.numero.sojodia.IApplication

val AppCompatActivity.app: IApplication
    get() = application as IApplication