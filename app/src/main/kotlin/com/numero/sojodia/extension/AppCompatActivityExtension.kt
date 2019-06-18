package com.numero.sojodia.extension

import androidx.appcompat.app.AppCompatActivity
import com.numero.sojodia.Module

val AppCompatActivity.module: Module
    get() = application as Module