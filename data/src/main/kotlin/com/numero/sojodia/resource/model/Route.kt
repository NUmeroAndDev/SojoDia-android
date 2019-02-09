package com.numero.sojodia.resource.model

enum class Route(val id: Int) {
    KutcToTk(0),
    KutcToTnd(1),
    TkToKutc(2),
    TndToKutc(3);

    companion object {
        fun find(id: Int): Route {
            val route = Route.values().find { it.id == id }
            return requireNotNull(route)
        }
    }
}