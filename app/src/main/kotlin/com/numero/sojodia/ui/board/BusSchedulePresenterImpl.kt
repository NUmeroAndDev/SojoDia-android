package com.numero.sojodia.ui.board

import com.numero.sojodia.model.*
import com.numero.sojodia.repository.BusDataRepository

class BusSchedulePresenterImpl(
        private val view: BusScheduleView,
        busDataRepository: BusDataRepository,
        private val reciprocate: Reciprocate
) : BusSchedulePresenter {

    private var week: Week = Week.getCurrentWeek()

    private var tkBusPosition = 0
    private var tndBusPosition = 0
    private val busData: BusData = busDataRepository.getBusData()

    private var tkTimeList: TkBusTimeList = TkBusTimeList.emptyList()

    private var tndTimeList: TndBusTimeList = TndBusTimeList.emptyList()

    init {
        this.view.setPresenter(this)
    }

    override fun subscribe() {}

    override fun unSubscribe() {}

    override fun setupBusTime(week: Week) {
        if (this.week != week) {
            this.week = week
            tkTimeList = TkBusTimeList.emptyList()
            tndTimeList = TndBusTimeList.emptyList()
        }
        tkTimeList = setupTkList()
        tndTimeList = setupTndList()

        view.showTkBusTimeList(tkTimeList)
        view.showTndBusTimeList(tndTimeList)

        tkBusPosition = tkTimeList.findNearBusTimePosition(Time())
        tndBusPosition = tndTimeList.findNearBusTimePosition(Time())

        if (tkBusPosition == NO_BUS_POSITION) {
            view.showTkNoBusLayout()
            view.hideTkNextButton()
            view.hideTkPreviewButton()
        } else {
            view.hideTkNoBusLayout()
            view.selectTkCurrentBusPosition(tkBusPosition)
            view.startTkCountDown(tkTimeList.value[tkBusPosition])
            if (tkBusPosition >= tkTimeList.size - 1) {
                view.hideTkNextButton()
            } else {
                view.showTkNextButton()
            }
            if (canPreviewTkTime()) {
                view.showTkPreviewButton()
            } else {
                view.hideTkPreviewButton()
            }
        }
        if (tndBusPosition == NO_BUS_POSITION) {
            view.showTndNoBusLayout()
            view.hideTndNextButton()
            view.hideTndPreviewButton()
        } else {
            view.hideTndNoBusLayout()
            view.selectTndCurrentBusPosition(tndBusPosition)
            view.startTndCountDown(tndTimeList.value[tndBusPosition])
            if (tndBusPosition >= tndTimeList.size - 1) {
                view.hideTndNextButton()
            } else {
                view.showTndNextButton()
            }
            if (canPreviewTndTime()) {
                view.showTndPreviewButton()
            } else {
                view.hideTndPreviewButton()
            }
        }
    }

    private fun setupTkList(): TkBusTimeList {
        return if (tkTimeList.isEmpty()) {
            val list = when (reciprocate) {
                Reciprocate.GOING -> busData.tkBusTimeListGoing
                        .asSequence()
                        .filter { it.week == week }
                        .toList()
                Reciprocate.RETURN -> busData.tkBusTimeListReturn
                        .asSequence()
                        .filter { it.week == week }
                        .toList()
            }
            tkTimeList = TkBusTimeList(list)
            tkTimeList
        } else {
            tkTimeList
        }
    }

    private fun setupTndList(): TndBusTimeList {
        return if (tndTimeList.isEmpty()) {
            val list = when (reciprocate) {
                Reciprocate.GOING -> busData.tndBusTimeListGoing
                        .asSequence()
                        .filter { busTime -> busTime.week == week }
                        .toList()
                Reciprocate.RETURN -> busData.tndBusTimeListReturn
                        .asSequence()
                        .filter { busTime -> busTime.week == week }
                        .toList()
            }
            tndTimeList = TndBusTimeList(list)
            tndTimeList
        } else {
            tndTimeList
        }
    }

    override fun nextTkBus() {
        if (tkBusPosition == tkTimeList.size - 1) {
            tkBusPosition = NO_BUS_POSITION
        }
        if (tkBusPosition == NO_BUS_POSITION) {
            view.hideTkNextButton()
            view.hideTkPreviewButton()
            view.showTkNoBusLayout()
            return
        }
        tkBusPosition += 1
        if (tkBusPosition >= tkTimeList.size - 1) {
            view.hideTkNextButton()
        } else {
            view.showTkNextButton()
        }
        if (canPreviewTkTime()) {
            view.showTkPreviewButton()
        } else {
            view.hideTkPreviewButton()
        }
        view.selectTkCurrentBusPosition(tkBusPosition)
        view.startTkCountDown(tkTimeList.value[tkBusPosition])
    }

    override fun previewTkBus() {
        tkBusPosition -= 1
        view.selectTkCurrentBusPosition(tkBusPosition)
        view.startTkCountDown(tkTimeList.value[tkBusPosition])
        if (canPreviewTkTime()) {
            view.showTkPreviewButton()
        } else {
            view.hideTkPreviewButton()
        }
        if (tkBusPosition >= tkTimeList.size - 1) {
            view.hideTkNextButton()
        } else {
            view.showTkNextButton()
        }
    }

    override fun nextTndBus() {
        if (tndBusPosition == tndTimeList.size - 1) {
            tndBusPosition = NO_BUS_POSITION
        }
        if (tndBusPosition == NO_BUS_POSITION) {
            view.hideTndNextButton()
            view.hideTndPreviewButton()
            view.showTndNoBusLayout()
            return
        }
        tndBusPosition += 1
        if (tndBusPosition >= tndTimeList.size - 1) {
            view.hideTndNextButton()
        } else {
            view.showTndNextButton()
        }
        if (canPreviewTndTime()) {
            view.showTndPreviewButton()
        } else {
            view.hideTndPreviewButton()
        }
        view.selectTndCurrentBusPosition(tndBusPosition)
        view.startTndCountDown(tndTimeList.value[tndBusPosition])
    }

    override fun previewTndBus() {
        tndBusPosition -= 1
        view.selectTndCurrentBusPosition(tndBusPosition)
        view.startTndCountDown(tndTimeList.value[tndBusPosition])
        if (canPreviewTndTime()) {
            view.showTndPreviewButton()
        } else {
            view.hideTndPreviewButton()
        }
        if (tndBusPosition >= tndTimeList.size - 1) {
            view.hideTndNextButton()
        } else {
            view.showTndNextButton()
        }
    }

    override fun showTimeTableDialog(route: Route) {
        view.showTimeTableDialog(route, reciprocate)
    }

    private fun canPreviewTkTime(): Boolean {
        if (tkBusPosition == NO_BUS_POSITION || tkBusPosition == 0) {
            return false
        }
        val busTime = tkTimeList.value[tkBusPosition - 1]
        return busTime.time > Time()
    }

    private fun canPreviewTndTime(): Boolean {
        if (tndBusPosition == NO_BUS_POSITION || tndBusPosition == 0) {
            return false
        }
        val busTime = tndTimeList.value[tndBusPosition - 1]
        return busTime.time > Time()
    }

    companion object {
        // TODO remove
        private const val NO_BUS_POSITION = BusTimeList.NO_BUS_POSITION
    }
}
