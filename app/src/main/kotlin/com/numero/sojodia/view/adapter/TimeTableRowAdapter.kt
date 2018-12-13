package com.numero.sojodia.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.numero.sojodia.R
import com.numero.sojodia.model.TimeTableRow
import com.numero.sojodia.view.TimeTableRowHolder
import java.util.*

class TimeTableRowAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        return when (ViewHolderType.findType(i)) {
            ViewHolderType.TIME_TABLE_ROW -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.holder_time_table_row, viewGroup, false)
                TimeTableRowHolder(view)
            }
            ViewHolderType.BOTTOM_DESCRIPTION -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.holder_time_table_description, viewGroup, false)
                DescriptionViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val size = tableRowList?.size ?: return super.getItemViewType(position)
        return if (size <= position) {
            ViewHolderType.BOTTOM_DESCRIPTION.typeId
        } else {
            ViewHolderType.TIME_TABLE_ROW.typeId
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val list = tableRowList ?: return
        if (holder is TimeTableRowHolder) {
            holder.apply {
                setTimeTableRow(list[position], isNotSchoolTerm)
                isCurrentHour = position == currentHour
            }
        }
    }

    override fun getItemId(position: Int): Long {
        val list = tableRowList ?: return 0
        if (list.size <= position) {
            return 0
        }
        return list[position].hour.toLong()
    }

    override fun getItemCount(): Int {
        val size = tableRowList?.size ?: return 0
        return size + 1
    }

    enum class ViewHolderType(val typeId: Int) {
        TIME_TABLE_ROW(0),
        BOTTOM_DESCRIPTION(1);

        companion object {
            fun findType(typeId: Int): ViewHolderType {
                return checkNotNull(values().find { it.typeId == typeId })
            }
        }
    }

    class DescriptionViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
