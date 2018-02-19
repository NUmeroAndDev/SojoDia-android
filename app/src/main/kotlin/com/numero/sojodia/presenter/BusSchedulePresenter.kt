package com.numero.sojodia.presenter

import com.numero.sojodia.contract.BusScheduleContract
import com.numero.sojodia.extension.isOverTime
import com.numero.sojodia.model.*
import com.numero.sojodia.repository.IBusDataRepository

class BusSchedulePresenter(private val view: BusScheduleContract.View,
                           private val busDataRepository: IBusDataRepository,
                           private val reciprocate: Reciprocate) : BusScheduleContract.Presenter {
    private lateinit var week: Week

    private var tkBusPosition = 0
    private var tndBusPosition = 0

    private var tkTimeList: MutableList<BusTime> = mutableListOf()
        get() {
            return if (field.isEmpty()) {
                when (reciprocate) {
                    Reciprocate.GOING -> busDataRepository.tkBusTimeListGoing
                            .filter { busTime -> busTime.week == week }.toMutableList()
                            .apply {
                                field = this
                            }
                    Reciprocate.RETURN -> busDataRepository.tkBusTimeListReturn
                            .filter { busTime -> busTime.week == week }.toMutableList()
                            .apply {
                                field = this
                            }
                }
            } else {
                field
            }
        }

    private var tndTimeList: MutableList<BusTime> = mutableListOf()
        get() {
            return if (field.isEmpty()) {
                when (reciprocate) {
                    Reciprocate.GOING -> busDataRepository.tndBusTimeListGoing
                            .filter { busTime -> busTime.week == week }.toMutableList()
                            .apply {
                                field = this
                            }
                    Reciprocate.RETURN -> busDataRepository.tndBusTimeListReturn
                            .filter { busTime -> busTime.week == week }.toMutableList()
                            .apply {
                                field = this
                            }
                }
            } else {
                field
            }
        }

    init {
        this.view.setPresenter(this)
    }

    override fun subscribe() {}

    override fun unSubscribe() {}

    override fun onTimeChanged(week: Week) {
        this.week = week
        tkTimeList.clear()
        tndTimeList.clear()
        view.showTkBusTimeList(tkTimeList)
        view.showTndBusTimeList(tndTimeList)

        tkBusPosition = findBusPosition(tkTimeList)
        tndBusPosition = findBusPosition(tndTimeList)

        if (tkBusPosition == NO_BUS_POSITION) {
            view.showTkNoBusLayout()
            view.hideTkNextButton()
            view.hideTkPreviewButton()
        } else {
            view.hideTkNoBusLayout()
            view.selectTkCurrentBusPosition(tkBusPosition)
            view.startTkCountDown(tkTimeList[tkBusPosition])
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
            view.startTndCountDown(tndTimeList[tndBusPosition])
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
        view.startTkCountDown(tkTimeList[tkBusPosition])
    }

    override fun previewTkBus() {
        tkBusPosition -= 1
        view.selectTkCurrentBusPosition(tkBusPosition)
        view.startTkCountDown(tkTimeList[tkBusPosition])
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
        view.startTndCountDown(tndTimeList[tndBusPosition])
    }

    override fun previewTndBus() {
        tndBusPosition -= 1
        view.selectTndCurrentBusPosition(tndBusPosition)
        view.startTndCountDown(tndTimeList[tndBusPosition])
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

    private fun findBusPosition(busTimeList: List<BusTime>): Int {
        busTimeList.forEachIndexed { index, busTime ->
            if (busTime.time.isOverTime(Time())) {
                return index
            }
        }
        return NO_BUS_POSITION
    }

    private fun canPreviewTkTime(): Boolean {
        if (tkBusPosition == NO_BUS_POSITION || tkBusPosition == 0) {
            return false
        }
        val busTime = tkTimeList[tkBusPosition - 1]
        return busTime.time.isOverTime(Time())
    }

    private fun canPreviewTndTime(): Boolean {
        if (tndBusPosition == NO_BUS_POSITION || tndBusPosition == 0) {
            return false
        }
        val busTime = tndTimeList[tndBusPosition - 1]
        return busTime.time.isOverTime(Time())
    }

    companion object {
        private const val NO_BUS_POSITION = -1
    }
}
