package com.numero.sojodia.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.numero.sojodia.R
import com.numero.sojodia.model.TimeTableRow
import com.numero.sojodia.view.TimeTableRowHolder
import java.util.*

class TimeTableRowAdapter : RecyclerView.Adapter<TimeTableRowHolder>() {

    var tableRowList: MutableList<TimeTableRow>? = null
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

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TimeTableRowHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.holder_time_table_row, viewGroup, false)
        return TimeTableRowHolder(view)
    }

    override fun onBindViewHolder(holder: TimeTableRowHolder, position: Int) {
        val list = tableRowList ?: return
        holder.apply {
            setTimeTableRow(list[position], isNotSchoolTerm)
            isCurrentHour = position == currentHour
        }
    }

    override fun getItemId(position: Int): Long {
        val list = tableRowList ?: return 0
        return list[position].hour.toLong()
    }

    override fun getItemCount(): Int {
        return tableRowList?.size ?: 0
    }
}
