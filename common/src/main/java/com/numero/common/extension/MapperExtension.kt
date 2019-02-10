package com.numero.common.extension

import com.numero.common.R
import com.numero.sojodia.repository.Theme

val Theme.styleRes: Int
    get() {
        return when (this) {
            Theme.DARK -> R.style.AppTheme_Dark_NoActionBar
            Theme.LIGHT -> R.style.AppTheme_Light_NoActionBar
        }
    }