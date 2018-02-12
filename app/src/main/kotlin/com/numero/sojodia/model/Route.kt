package com.numero.sojodia.model

import com.numero.sojodia.R

enum class Route(val stationStringRes: Int) {

    TK(
            R.string.station_tk
    ),

    TND(
            R.string.station_tnd
    );

    companion object {
        fun getRoute(ordinal: Int): Route = if (Route.values().size <= ordinal) {
            TK
        } else Route.values()[ordinal]
    }
}
