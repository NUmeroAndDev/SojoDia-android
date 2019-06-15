package com.numero.sojodia.ui.board

import com.numero.sojodia.model.BusTime
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.model.Route
import com.numero.sojodia.model.Week

interface BusScheduleContract {

    interface View : com.numero.sojodia.core.View<Presenter> {

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

    interface Presenter : com.numero.sojodia.core.Presenter {
        fun onTimeChanged(week: Week)

        fun nextTkBus()

        fun previewTkBus()

        fun nextTndBus()

        fun previewTndBus()

        fun showTimeTableDialog(route: Route)
    }
}
