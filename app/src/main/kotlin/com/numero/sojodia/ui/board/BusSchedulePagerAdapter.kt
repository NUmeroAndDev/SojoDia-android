package com.numero.sojodia.ui.board

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.numero.sojodia.model.Reciprocate

class BusSchedulePagerAdapter(
        fragmentActivity: FragmentActivity,
        private val reciprocateList: List<Reciprocate>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = reciprocateList.size

    override fun createFragment(position: Int): Fragment {
        return BusScheduleFragment.newInstance(reciprocateList[position])
    }
}