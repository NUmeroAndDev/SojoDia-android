package com.numero.sojodia.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.numero.sojodia.model.TimeTableRow
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.holder_time_table_row.*
import java.util.*

class TimeTableRowHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    var isCurrentHour: Boolean = false
        set(value) {
            field = value
            rowLayout.isActivated = value
        }

    fun setTimeTableRow(timeTableRow: TimeTableRow, isNotSchoolTerm: Boolean) {
        hourTextView.text = "%02d".format(Locale.ENGLISH, timeTableRow.hour)
        weekdayTextView.text = timeTableRow.createOnWeekdayText(isNotSchoolTerm)
        saturdayTextView.text = timeTableRow.createOnSaturdayText(isNotSchoolTerm)
        sundayTextView.text = timeTableRow.createOnSundayText(isNotSchoolTerm)
    }
}
