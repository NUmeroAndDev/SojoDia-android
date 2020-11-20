package com.numero.sojodia.ui

import com.numero.sojodia.R

sealed class Screen(val name: String) {
    open val route: String
        get() = name

    object Settings : Screen("settings")

    object License : Screen("credentialForm") {
        override val route: String
            get() = throw Exception("Use routeId instead of use this.")
        val routeId: Int = R.id.nav_oss_licenses
    }
}