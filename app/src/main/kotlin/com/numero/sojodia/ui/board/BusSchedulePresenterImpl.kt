package com.numero.sojodia.ui.board

import com.numero.sojodia.model.*
import com.numero.sojodia.repository.BusDataRepository

class BusSchedulePresenterImpl(
        private val view: BusScheduleView,
        busDataRepository: BusDataRepository,
        private val reciprocate: Reciprocate
) : BusSchedulePresenter {

    private var week: Week = Week.getCurrentWeek()

    private val tkBusTimeListPosition = BusTimeListPosition()
    private val tndBusTimeListPosition = BusTimeListPosition()
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

        val nearTkBusTimePosition = tkTimeList.findNearBusTimePosition(Time())
        tkBusTimeListPosition.apply {
            set(nearTkBusTimePosition)
            setMaxPosition(tkTimeList.size - 1)
            setMinPosition(nearTkBusTimePosition)
        }

        val nearTndBusTimePosition = tndTimeList.findNearBusTimePosition(Time())
        tndBusTimeListPosition.apply {
            set(nearTndBusTimePosition)
            setMaxPosition(tndTimeList.size - 1)
            setMinPosition(nearTndBusTimePosition)
        }

        if (tkBusTimeListPosition.isNoNextAndPrevious) {
            view.showTkNoBusLayout()
            view.hideTkNextButton()
            view.hideTkPreviewButton()
        } else {
            view.hideTkNoBusLayout()
            updateTkBusView()
        }
        if (tndBusTimeListPosition.isNoNextAndPrevious) {
            view.showTndNoBusLayout()
            view.hideTndNextButton()
            view.hideTndPreviewButton()
        } else {
            view.hideTndNoBusLayout()
            updateTndBusView()
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
        if (tkBusTimeListPosition.canNext().not()) {
            tkBusTimeListPosition.setMinPosition(0)
            tkBusTimeListPosition.set(BusTimeListPosition.NO_BUS_POSITION)
        }
        if (tkBusTimeListPosition.isNoNextAndPrevious) {
            view.hideTkNextButton()
            view.hideTkPreviewButton()
            view.showTkNoBusLayout()
            return
        }
        tkBusTimeListPosition.next()
        tkBusTimeListPosition.setMinPosition(tkBusTimeListPosition.value)
        updateTkBusView()
    }

    override fun previewTkBus() {
        tkBusTimeListPosition.previous()
        updateTkBusView()
    }

    override fun nextTndBus() {
        if (tndBusTimeListPosition.canNext().not()) {
            tndBusTimeListPosition.setMaxPosition(0)
            tndBusTimeListPosition.set(BusTimeListPosition.NO_BUS_POSITION)
        }
        if (tndBusTimeListPosition.isNoNextAndPrevious) {
            view.hideTndNextButton()
            view.hideTndPreviewButton()
            view.showTndNoBusLayout()
            return
        }
        tndBusTimeListPosition.next()
        tndBusTimeListPosition.setMinPosition(tndBusTimeListPosition.value)
        updateTndBusView()
    }

    override fun previewTndBus() {
        tndBusTimeListPosition.previous()
        updateTndBusView()
    }

    override fun showTimeTableDialog(route: Route) {
        view.showTimeTableDialog(route, reciprocate)
    }

    private fun updateTkBusView() {
        if (tkBusTimeListPosition.canNext()) {
            view.showTkNextButton()
        } else {
            view.hideTkNextButton()
        }
        if (tkBusTimeListPosition.canPrevious()) {
            view.showTkPreviewButton()
        } else {
            view.hideTkPreviewButton()
        }
        view.selectTkCurrentBusPosition(tkBusTimeListPosition.value)
        view.startTkCountDown(tkTimeList.value[tkBusTimeListPosition.value])
    }

    private fun updateTndBusView() {
        if (tndBusTimeListPosition.canNext()) {
            view.showTndNextButton()
        } else {
            view.hideTndNextButton()
        }
        if (tndBusTimeListPosition.canPrevious()) {
            view.showTndPreviewButton()
        } else {
            view.hideTndPreviewButton()
        }
        view.selectTndCurrentBusPosition(tndBusTimeListPosition.value)
        view.startTndCountDown(tndTimeList.value[tndBusTimeListPosition.value])
    }
}
