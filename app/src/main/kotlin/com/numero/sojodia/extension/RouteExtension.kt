package com.numero.sojodia.extension

import com.numero.sojodia.R
import com.numero.sojodia.model.Route

val Route.stationTitleRes: Int
    get() = when (this) {
        Route.TK -> R.string.station_tk
        Route.TND -> R.string.station_tnd
    }