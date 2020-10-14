package com.numero.sojodia.ui.timetable

import com.numero.sojodia.model.TimeTableRowList

interface TimeTableView {

    fun showTimeTableRowList(list: TimeTableRowList)
}