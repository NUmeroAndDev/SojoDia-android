package com.numero.sojodia.view.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.numero.sojodia.fragment.BusScheduleFragment
import com.numero.sojodia.model.Reciprocate

class BusScheduleFragmentPagerAdapter(private val context: Context, fragmentManager: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
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
