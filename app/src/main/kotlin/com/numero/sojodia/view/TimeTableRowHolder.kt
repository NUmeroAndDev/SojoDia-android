package com.numero.sojodia.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.numero.sojodia.model.TimeTableRow
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.holder_time_table_row.*
import java.util.*

class TimeTableRowHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    var timeTableRow: TimeTableRow? = null
        set(value) {
            value ?: return
            field = value
            hourTextView.text = "%02d".format(Locale.ENGLISH, value.hour)
            weekdayTextView.text = value.timeOnWeekdayText
            saturdayTextView.text = value.timeOnSaturdayText
            sundayTextView.text = value.timeOnSundayText
        }

    var isCurrentHour: Boolean = false
        set(value) {
            field = value
            rowLayout.isActivated = value
        }
}
