package com.numero.sojodia.extension

import com.numero.sojodia.R
import com.numero.sojodia.repository.Theme

val Theme.styleRes: Int
    get() {
        return when (this) {
            Theme.DARK -> R.style.AppTheme_Dark_NoActionBar
            Theme.LIGHT -> R.style.AppTheme_Light_NoActionBar
        }
    }