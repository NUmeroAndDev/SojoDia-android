package com.numero.sojodia.ui.timetable

import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.model.Route
import com.numero.sojodia.model.TimeTableRow
import com.numero.sojodia.view.IView

interface TimeTableView : IView<TimeTablePresenter> {

    fun showTimeTableRowList(timeTableRowList: MutableList<TimeTableRow>)

    fun showCurrentRoute(route: Route)

    fun showCurrentReciprocate(reciprocate: Reciprocate)
}