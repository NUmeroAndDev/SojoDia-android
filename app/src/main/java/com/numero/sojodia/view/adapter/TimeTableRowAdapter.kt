package com.numero.sojodia.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.numero.sojodia.R
import com.numero.sojodia.model.TimeTableRow
import com.numero.sojodia.view.TimeTableRowHolder

import java.util.Calendar

class TimeTableRowAdapter(private val rowList: List<TimeTableRow>?) : RecyclerView.Adapter<TimeTableRowHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TimeTableRowHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.holder_time_table_row, viewGroup, false)
        return TimeTableRowHolder(view)
    }

    override fun onBindViewHolder(holder: TimeTableRowHolder, position: Int) {
        rowList ?: return
        holder.apply {
            timeTableRow = rowList[position]
            isCurrentHour = position == Calendar.getInstance().get(Calendar.HOUR_OF_DAY) - 6
        }
    }

    override fun getItemCount(): Int {
        return rowList?.size ?: 0
    }
}
