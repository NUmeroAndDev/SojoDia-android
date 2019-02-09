package com.numero.sojodia.extension

import androidx.appcompat.app.AppCompatActivity
import com.numero.common.IModule

val AppCompatActivity.module: IModule
    get() = application as IModule