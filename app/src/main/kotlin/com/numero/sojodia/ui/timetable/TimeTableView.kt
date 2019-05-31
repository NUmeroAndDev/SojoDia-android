package com.numero.sojodia.ui.timetable

import com.numero.sojodia.model.TimeTableRowList
import com.numero.sojodia.view.IView

interface TimeTableView : IView<TimeTablePresenter> {

    fun showTimeTableRowList(timeTableRowList: TimeTableRowList)
}