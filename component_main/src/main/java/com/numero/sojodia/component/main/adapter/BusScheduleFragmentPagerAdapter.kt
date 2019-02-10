package com.numero.sojodia.component.main.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.numero.sojodia.component.main.BusScheduleFragment
import com.numero.sojodia.component.main.titleStringRes
import com.numero.sojodia.model.Reciprocate

class BusScheduleFragmentPagerAdapter(private val context: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return BusScheduleFragment.newInstance(Reciprocate.findReciprocate(position))
    }

    override fun getCount(): Int {
        return Reciprocate.values().size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val reciprocate = Reciprocate.findReciprocate(position)
        return context.getString(reciprocate.titleStringRes)
    }
}
