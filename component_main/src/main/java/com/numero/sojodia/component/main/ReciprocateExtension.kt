package com.numero.sojodia.component.main

import com.numero.sojodia.model.Reciprocate

val Reciprocate.titleStringRes: Int
    get() = when (this) {
        Reciprocate.GOING -> R.string.going_to_school
        Reciprocate.RETURN -> R.string.coming_home
    }