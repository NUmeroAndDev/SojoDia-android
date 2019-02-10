package com.numero.sojodia.component.main

import com.numero.sojodia.model.*
import com.numero.sojodia.repository.IBusDataRepository

class TimeTablePresenter(
        private val view: TimeTableContract.View,
        private val busDataRepository: IBusDataRepository,
        private val route: Route,
        private val reciprocate: Reciprocate
) : TimeTableContract.Presenter {

    private val timeTableRowList = mutableListOf<TimeTableRow>()

    init {
        view.setPresenter(this)

        // バスは6時から23時しか動いていない
        (6..23).mapTo(timeTableRowList) { TimeTableRow(it) }

        val list = if (route == Route.TK) {
            setupTkBusTimeList()
        } else {
            setupTndBusTimeList()
        }
        mapTimeTableRow(list)
    }

    override fun subscribe() {
        view.showCurrentRoute(route)
        view.showCurrentReciprocate(reciprocate)
        view.showTimeTableRowList(timeTableRowList)
    }

    override fun unSubscribe() {
    }

    private fun setupTkBusTimeList(): List<BusTime> {
        return if (reciprocate == Reciprocate.GOING) busDataRepository.tkBusTimeListGoing else busDataRepository.tkBusTimeListReturn
    }

    private fun setupTndBusTimeList(): List<BusTime> {
        return if (reciprocate == Reciprocate.GOING) busDataRepository.tndBusTimeListGoing else busDataRepository.tndBusTimeListReturn
    }

    private fun mapTimeTableRow(list: List<BusTime>) {
        list.forEach {
            val hour = it.time.hour - 6
            when (it.week) {
                Week.WEEKDAY -> timeTableRowList[hour].addBusTimeOnWeekday(it)
                Week.SATURDAY -> timeTableRowList[hour].addBusTimeOnSaturday(it)
                Week.SUNDAY -> timeTableRowList[hour].addBusTimeOnSunday(it)
            }
        }
    }

}
