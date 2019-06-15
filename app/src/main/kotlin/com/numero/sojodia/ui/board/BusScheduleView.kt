package com.numero.sojodia.ui.board

import com.numero.sojodia.core.View
import com.numero.sojodia.model.BusTime
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.model.Route

interface BusScheduleView : View<BusSchedulePresenter> {
    fun showTkBusTimeList(busTimeList: MutableList<BusTime>)

    fun showTndBusTimeList(busTimeList: MutableList<BusTime>)

    fun selectTkCurrentBusPosition(position: Int)

    fun startTkCountDown(busTime: BusTime)

    fun selectTndCurrentBusPosition(position: Int)

    fun startTndCountDown(busTime: BusTime)

    fun showTkNextButton()

    fun showTkPreviewButton()

    fun showTkNoBusLayout()

    fun showTndNextButton()

    fun showTndPreviewButton()

    fun showTndNoBusLayout()

    fun hideTkNextButton()

    fun hideTkPreviewButton()

    fun hideTkNoBusLayout()

    fun hideTndNextButton()

    fun hideTndPreviewButton()

    fun hideTndNoBusLayout()

    fun showTimeTableDialog(route: Route, reciprocate: Reciprocate)
}