package com.numero.sojodia.ui.timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.numero.sojodia.databinding.HolderTimeTableDescriptionBinding
import com.numero.sojodia.databinding.HolderTimeTableRowBinding
import com.numero.sojodia.model.TimeTableRow
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
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            VIEW_TYPE_CELL -> {
                val binding = HolderTimeTableRowBinding.inflate(layoutInflater, viewGroup, false)
                TimeTableRowHolder(binding)
            }
            VIEW_TYPE_FOOTER -> {
                val binding = HolderTimeTableDescriptionBinding.inflate(layoutInflater, viewGroup, false)
                DescriptionViewHolder(binding)
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

    class DescriptionViewHolder(binding: HolderTimeTableDescriptionBinding) : RecyclerView.ViewHolder(binding.root)

    class TimeTableRowHolder(
            private val binding: HolderTimeTableRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        var isCurrentHour: Boolean = false
            set(value) {
                field = value
                binding.root.isActivated = value
            }

        fun setTimeTableRow(timeTableRow: TimeTableRow, isNotSchoolTerm: Boolean) {
            binding.hourTextView.text = "%02d".format(Locale.ENGLISH, timeTableRow.hour)
            binding.weekdayTextView.text = timeTableRow.getOnWeekdayText(isNotSchoolTerm)
            binding.saturdayTextView.text = timeTableRow.getOnSaturdayText(isNotSchoolTerm)
            binding.sundayTextView.text = timeTableRow.getOnSundayText(isNotSchoolTerm)
        }
    }
}
