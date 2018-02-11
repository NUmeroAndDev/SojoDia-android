package com.numero.sojodia.view.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.numero.sojodia.fragment.BusScheduleFragment
import com.numero.sojodia.model.Reciprocate

class BusScheduleFragmentPagerAdapter(private val context: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return BusScheduleFragment.newInstance(Reciprocate.getReciprocate(position))
    }

    override fun getCount(): Int {
        return Reciprocate.values().size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val reciprocate = Reciprocate.getReciprocate(position)
        return context.getString(reciprocate.titleStringRes)
    }
}
