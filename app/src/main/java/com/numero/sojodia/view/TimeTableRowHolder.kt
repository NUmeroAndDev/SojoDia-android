package com.numero.sojodia.view

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View

import com.numero.sojodia.R
import com.numero.sojodia.model.TimeTableRow
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.holder_time_table_row.*
import java.util.*

class TimeTableRowHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    var timeTableRow: TimeTableRow? = null
        set(value) {
            value ?: return
            field = value
            hourTextView.text = String.format(Locale.ENGLISH, "%02d", value.hour)
            weekdayTextView.text = value.timeOnWeekdayText
            saturdayTextView.text = value.timeOnSaturdayText
            sundayTextView.text = value.timeOnSundayText
        }

    var isCurrentHour: Boolean = false
        set(value) {
            field = value
            rowLayout.apply {
                if (value) {
                    setBackgroundColor(ContextCompat.getColor(context, R.color.time_table_dialog_row_highlight))
                } else {
                    background = null
                }
            }
        }
}
