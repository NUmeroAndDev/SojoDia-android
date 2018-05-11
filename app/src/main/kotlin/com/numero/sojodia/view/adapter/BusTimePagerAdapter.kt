package com.numero.sojodia.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.numero.sojodia.R
import com.numero.sojodia.model.BusTime
import kotlinx.android.synthetic.main.pager_bus_time.view.*
import java.util.*

class BusTimePagerAdapter : PagerAdapter() {

    private var busTimeList: List<BusTime>? = null

    fun setBusTimeList(busTimeList: List<BusTime>) {
        this.busTimeList = busTimeList
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.pager_bus_time, container, false)

        val busTimeList = busTimeList ?: return view

        if (position >= busTimeList.size) {
            return view
        }

        val busTime = busTimeList[position]
        view.busTimeTextView.text = String.format(Locale.ENGLISH, "%02d:%02d", busTime.time.hour, busTime.time.min)
        view.busDescriptionTextView.apply {
            if (busTime.isNonstop) {
                visibility = View.VISIBLE
                setText(R.string.bus_type_nonstop)
            } else {
                visibility = View.GONE
            }
            if (position == busTimeList.size - 1) {
                // 最終バス
                visibility = View.VISIBLE
                setText(R.string.last_bus)
            }
        }

        val nextBusGroup = view.nextBusGroup
        if (position == busTimeList.size - 1) {
            nextBusGroup.visibility = View.GONE
        } else {
            nextBusGroup.visibility = View.VISIBLE
            val nextBusTime = busTimeList[position + 1]
            view.nextBusTimeTextView.text = String.format(Locale.ENGLISH, "%02d:%02d", nextBusTime.time.hour, nextBusTime.time.min)
            view.nextBusDescriptionTextView.apply {
                if (nextBusTime.isNonstop) {
                    visibility = View.VISIBLE
                    setText(R.string.bus_type_nonstop)
                } else {
                    visibility = View.GONE
                }
            }
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }

    override fun getCount(): Int {
        return busTimeList?.size ?: return 0
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view === any
    }

    override fun getItemPosition(any: Any): Int {
        return androidx.viewpager.widget.PagerAdapter.POSITION_NONE
    }
}
