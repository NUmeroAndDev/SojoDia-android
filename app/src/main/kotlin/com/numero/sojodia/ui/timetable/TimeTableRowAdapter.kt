package com.numero.sojodia.ui.timetable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.numero.sojodia.R
import com.numero.sojodia.model.TimeTableRow
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.holder_time_table_row.*
import java.util.*

class TimeTableRowAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var tableRowList: List<TimeTableRow> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var isNotSchoolTerm: Boolean = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) - 6

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CELL -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.holder_time_table_row, viewGroup, false)
                TimeTableRowHolder(view)
            }
            VIEW_TYPE_FOOTER -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.holder_time_table_description, viewGroup, false)
                DescriptionViewHolder(view)
            }
            else -> throw IllegalArgumentException("Not defined viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val size = tableRowList.size
        return if (size <= position) {
            VIEW_TYPE_FOOTER
        } else {
            VIEW_TYPE_CELL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TimeTableRowHolder) {
            holder.apply {
                setTimeTableRow(tableRowList[position], isNotSchoolTerm)
                isCurrentHour = position == currentHour
            }
        }
    }

    override fun getItemId(position: Int): Long {
        if (tableRowList.size <= position) {
            return 0
        }
        return tableRowList[position].hour.toLong()
    }

    override fun getItemCount(): Int {
        return tableRowList.size + 1
    }

    companion object {
        private const val VIEW_TYPE_CELL = 0
        private const val VIEW_TYPE_FOOTER = 1
    }

    class DescriptionViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer

    class TimeTableRowHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        var isCurrentHour: Boolean = false
            set(value) {
                field = value
                rowLayout.isActivated = value
            }

        fun setTimeTableRow(timeTableRow: TimeTableRow, isNotSchoolTerm: Boolean) {
            hourTextView.text = "%02d".format(Locale.ENGLISH, timeTableRow.hour)
            weekdayTextView.text = timeTableRow.getOnWeekdayText(isNotSchoolTerm)
            saturdayTextView.text = timeTableRow.getOnSaturdayText(isNotSchoolTerm)
            sundayTextView.text = timeTableRow.getOnSundayText(isNotSchoolTerm)
        }
    }
}
