package com.numero.sojodia.ui.timetable

import com.numero.sojodia.model.*
import com.numero.sojodia.repository.BusDataRepository

class TimeTablePresenterImpl(
        private val view: TimeTableView,
        busDataRepository: BusDataRepository,
        route: Route,
        private val reciprocate: Reciprocate
) : TimeTablePresenter {

    private val timeTableRowList: TimeTableRowList
    private val busData: BusData = busDataRepository.getBusData()

    init {
        val list = if (route == Route.TK) {
            setupTkBusTimeList()
        } else {
            setupTndBusTimeList()
        }
        timeTableRowList = TimeTableRowList.mapToTimeTableRowList(list)
    }

    override fun subscribe() {
        view.showTimeTableRowList(timeTableRowList)
    }

    override fun unSubscribe() {
    }

    private fun setupTkBusTimeList(): List<BusTime> {
        return if (reciprocate == Reciprocate.GOING) busData.tkBusTimeListGoing else busData.tkBusTimeListReturn
    }

    private fun setupTndBusTimeList(): List<BusTime> {
        return if (reciprocate == Reciprocate.GOING) busData.tndBusTimeListGoing else busData.tndBusTimeListReturn
    }

}
