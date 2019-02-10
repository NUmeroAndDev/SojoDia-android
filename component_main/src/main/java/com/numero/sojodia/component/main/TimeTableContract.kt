package com.numero.sojodia.component.main

import com.numero.common.IPresenter
import com.numero.common.IView
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.model.Route

interface TimeTableContract {

    interface View : IView<Presenter> {

        fun showTimeTableRowList(timeTableRowList: MutableList<TimeTableRow>)

        fun showCurrentRoute(route: Route)

        fun showCurrentReciprocate(reciprocate: Reciprocate)
    }

    interface Presenter : IPresenter
}
