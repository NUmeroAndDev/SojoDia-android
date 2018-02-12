package com.numero.sojodia.presenter

import com.numero.sojodia.contract.TimeTableContract
import com.numero.sojodia.model.*
import com.numero.sojodia.repository.BusDataRepository

class TimeTablePresenter(private val view: TimeTableContract.View,
                         private val busDataRepository: BusDataRepository,
                         private val route: Route,
                         private val reciprocate: Reciprocate) : TimeTableContract.Presenter {

    private val timeTableRowList = mutableListOf<TimeTableRow>()

    init {
        view.setPresenter(this)

        // バスは6時から23時しか動いていない
        (6..23).mapTo(timeTableRowList) { TimeTableRow(it) }
    }

    override fun subscribe() {
        view.showCurrentRoute(route)
        view.showCurrentReciprocate(reciprocate)

        val list = if (route == Route.TK) {
            setupTkBusTimeList()
        } else {
            setupTndBusTimeList()
        }
        mapTimeTableRow(list)

        view.showTimeTableRowList(timeTableRowList)
    }

    override fun unSubscribe() {
    }

    private fun setupTkBusTimeList(): MutableList<BusTime> {
        return if (reciprocate == Reciprocate.GOING) busDataRepository.tkBusTimeListGoing else busDataRepository.tkBusTimeListReturn
    }

    private fun setupTndBusTimeList(): MutableList<BusTime> {
        return if (reciprocate == Reciprocate.GOING) busDataRepository.tndBusTimeListGoing else busDataRepository.tndBusTimeListReturn
    }

    private fun mapTimeTableRow(list: MutableList<BusTime>) {
        list.forEach {
            when (it.week) {
                Week.WEEKDAY -> timeTableRowList[it.hour - 6].addBusTimeOnWeekday(it)
                Week.SATURDAY -> timeTableRowList[it.hour - 6].addBusTimeOnSaturday(it)
                Week.SUNDAY -> timeTableRowList[it.hour - 6].addBusTimeOnSunday(it)
            }
        }
    }

}
