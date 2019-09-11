package com.numero.sojodia.ui.board

import com.numero.sojodia.core.Presenter
import com.numero.sojodia.model.Route
import com.numero.sojodia.model.Week

interface BusSchedulePresenter : Presenter {
    fun setupBusTime(week: Week)

    fun nextTkBus()

    fun previewTkBus()

    fun nextTndBus()

    fun previewTndBus()

    fun showTimeTableDialog(route: Route)
}