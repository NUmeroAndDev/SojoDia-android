package com.numero.sojodia.data

data class BusRouteId(val value: Int) {

    companion object {
        const val KUTC_TO_TK_ID = 0
        const val KUTC_TO_TND_ID = 1
        const val TK_TO_KUTC_ID = 2
        const val TND_TO_KUTC_ID = 3

        fun from(route: BusRoute): BusRouteId {
            val value = when (route) {
                BusRoute.KUTC_TO_TK -> KUTC_TO_TK_ID
                BusRoute.KUTC_TO_TND -> KUTC_TO_TND_ID
                BusRoute.TK_TO_KUTC -> TK_TO_KUTC_ID
                BusRoute.TND_TO_KUTC -> TND_TO_KUTC_ID
            }
            return BusRouteId(value)
        }
    }
}

enum class BusRoute {
    KUTC_TO_TK,
    KUTC_TO_TND,
    TK_TO_KUTC,
    TND_TO_KUTC;

    companion object {
        fun from(id: Int): BusRoute {
            return when (id) {
                BusRouteId.KUTC_TO_TK_ID -> BusRoute.KUTC_TO_TK
                BusRouteId.KUTC_TO_TND_ID -> BusRoute.KUTC_TO_TND
                BusRouteId.TK_TO_KUTC_ID -> BusRoute.TK_TO_KUTC
                BusRouteId.TND_TO_KUTC_ID -> BusRoute.TND_TO_KUTC
                else -> throw IllegalArgumentException()
            }
        }
    }
}