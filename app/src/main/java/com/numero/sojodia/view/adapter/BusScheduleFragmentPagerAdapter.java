package com.numero.sojodia.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.numero.sojodia.fragment.BusScheduleFragment;
import com.numero.sojodia.model.Reciprocate;

public class BusScheduleFragmentPagerAdapter extends FragmentPagerAdapter {

    private final Context context;

    public BusScheduleFragmentPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return BusScheduleFragment.Companion.newInstance(Reciprocate.getReciprocate(position));
    }

    @Override
    public int getCount() {
        return Reciprocate.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Reciprocate reciprocate = Reciprocate.getReciprocate(position);
        return context.getString(reciprocate.getTitleStringRes());
    }
}
