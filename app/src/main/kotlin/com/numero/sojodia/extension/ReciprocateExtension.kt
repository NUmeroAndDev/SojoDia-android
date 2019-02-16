package com.numero.sojodia.extension

import com.numero.sojodia.R
import com.numero.sojodia.model.Reciprocate

val Reciprocate.titleStringRes: Int
    get() = when (this) {
        Reciprocate.GOING -> R.string.going_to_school
        Reciprocate.RETURN -> R.string.coming_home
    }