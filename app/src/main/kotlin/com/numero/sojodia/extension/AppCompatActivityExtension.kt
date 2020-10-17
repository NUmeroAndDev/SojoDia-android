package com.numero.sojodia.extension

import androidx.appcompat.app.AppCompatActivity
import com.numero.sojodia.Component

val AppCompatActivity.component: Component
    get() = application as Component