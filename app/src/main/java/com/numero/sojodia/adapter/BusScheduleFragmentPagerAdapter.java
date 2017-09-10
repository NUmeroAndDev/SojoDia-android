package com.numero.sojodia.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.numero.sojodia.R;
import com.numero.sojodia.fragment.BusScheduleFragment;

public class BusScheduleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    // FIXME 宣言場所を変える
    public final static int RECIPROCATE_GOING = 0;
    public final static int RECIPROCATE_RETURN = 1;

    public BusScheduleFragmentPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return BusScheduleFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case RECIPROCATE_GOING:
                return context.getString(R.string.going_to_school);
            case RECIPROCATE_RETURN:
                return context.getString(R.string.coming_home);
        }
        return null;
    }
}
