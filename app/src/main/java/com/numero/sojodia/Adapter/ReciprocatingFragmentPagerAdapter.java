package com.numero.sojodia.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.numero.sojodia.ReciprocatingFragment;

public class ReciprocatingFragmentPagerAdapter extends FragmentPagerAdapter {

    public ReciprocatingFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
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
//            ToDo string resource
            case 0:
                return "登校";
            case 1:
                return "下校";
        }
        return null;
    }
}
