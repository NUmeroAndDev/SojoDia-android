package com.numero.sojodia.model

enum class BusRoute(val id: Int) {
    KutcToTk(0),
    KutcToTnd(1),
    TkToKutc(2),
    TndToKutc(3);

    companion object {
        fun find(id: Int): BusRoute {
            val route = BusRoute.values().find { it.id == id }
            return requireNotNull(route)
        }
    }
}