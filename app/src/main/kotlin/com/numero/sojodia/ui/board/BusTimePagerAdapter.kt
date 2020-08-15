package com.numero.sojodia.ui.board

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.numero.sojodia.R
import com.numero.sojodia.databinding.PagerBusTimeBinding
import com.numero.sojodia.model.BusTime

class BusTimePagerAdapter : PagerAdapter() {

    private var busTimeList: List<BusTime>? = null

    fun setBusTimeList(busTimeList: List<BusTime>) {
        this.busTimeList = busTimeList
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = PagerBusTimeBinding.inflate(LayoutInflater.from(container.context), container, false)

        val busTimeList = busTimeList ?: return binding.root

        if (position >= busTimeList.size) {
            return binding.root
        }

        val busTime = busTimeList[position]
        binding.busTimeTextView.text = "%02d:%02d".format(busTime.time.hour, busTime.time.min)
        binding.busDescriptionTextView.apply {
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

        if (position != busTimeList.size - 1) {
            val nextBusTime = busTimeList[position + 1]
            binding.nextBusTimeTextView.text = "%02d:%02d".format(nextBusTime.time.hour, nextBusTime.time.min)
            binding.nextBusDescriptionTextView.apply {
                if (nextBusTime.isNonstop) {
                    visibility = View.VISIBLE
                    setText(R.string.bus_type_nonstop)
                } else {
                    visibility = View.GONE
                }
            }
        }

        container.addView(binding.root)
        return binding.root
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
        return PagerAdapter.POSITION_NONE
    }
}
