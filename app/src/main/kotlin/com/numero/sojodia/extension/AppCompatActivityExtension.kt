package com.numero.sojodia.extension

import androidx.appcompat.app.AppCompatActivity
import com.numero.sojodia.ILegacyModule

val AppCompatActivity.module: ILegacyModule
    get() = application as ILegacyModule