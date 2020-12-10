package com.numero.sojodia.ui.board

import com.numero.sojodia.model.BusTime
import com.numero.sojodia.model.Route
import java.util.*

data class BusBoardUiState(
    val tkBusBoardSchedule: BusBoardSchedule,
    val tndBusBoardSchedule: BusBoardSchedule,
    val currentDate: Date
) {
    companion object {
        fun empty() = BusBoardUiState(
            tkBusBoardSchedule = BusBoardSchedule(
                route = Route.TK,
                nearBusTime = null,
                nextBusTime = null,
                canPrevBusTime = false,
                canNextBusTime = false
            ),
            tndBusBoardSchedule = BusBoardSchedule(
                route = Route.TND,
                nearBusTime = null,
                nextBusTime = null,
                canPrevBusTime = false,
                canNextBusTime = false
            ),
            currentDate = Calendar.getInstance().time
        )
    }
}

data class BusBoardSchedule(
    val route: Route,
    val nearBusTime: BusTime?,
    val nextBusTime: BusTime?,
    val canPrevBusTime: Boolean,
    val canNextBusTime: Boolean,
    // TODO count time
)