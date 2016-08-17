package com.numero.sojodia.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.numero.sojodia.R;
import com.numero.sojodia.ReciprocatingFragment;

public class ReciprocatingFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public ReciprocatingFragmentPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return ReciprocatingFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.going_to_school);
            case 1:
                return context.getString(R.string.coming_home);
        }
        return null;
    }
}
