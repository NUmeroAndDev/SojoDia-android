package com.numero.sojodia.ui.board

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.numero.sojodia.extension.titleStringRes

import com.numero.sojodia.model.Reciprocate

class BusScheduleFragmentPagerAdapter(
        private val context: Context,
        fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val reciprocateList: List<Reciprocate> = listOf(Reciprocate.GOING, Reciprocate.RETURN)

    override fun getItem(position: Int): Fragment {
        return BusScheduleFragment.newInstance(reciprocateList[position])
    }

    override fun getCount(): Int = reciprocateList.size

    override fun getPageTitle(position: Int): CharSequence? {
        val reciprocate = reciprocateList[position]
        return context.getString(reciprocate.titleStringRes)
    }
}
