package com.numero.sojodia.ui.timetable

import com.numero.sojodia.model.TimeTableRowList
import com.numero.sojodia.core.View

interface TimeTableView : View<TimeTablePresenter> {

    fun showTimeTableRowList(list: TimeTableRowList)
}