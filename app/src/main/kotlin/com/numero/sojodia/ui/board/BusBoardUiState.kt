package com.numero.sojodia.ui.board

import com.numero.sojodia.model.BusTime
import com.numero.sojodia.model.Route
import java.util.*

data class BusBoardUiState(
    val tkBusBoardSchedule: BusBoardSchedule,
    val tndBusBoardSchedule: BusBoardSchedule,
    val currentDate: Date
)

data class BusBoardSchedule(
    val route: Route,
    val nearBusTime: BusTime,
    val nextBusTime: BusTime,
    // TODO count time
)