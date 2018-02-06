package com.numero.sojodia.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.numero.sojodia.fragment.BusScheduleFragment;
import com.numero.sojodia.model.Reciprocate;
import com.numero.sojodia.presenter.BusSchedulePresenter;
import com.numero.sojodia.repository.BusDataRepository;

public class BusScheduleFragmentPagerAdapter extends FragmentPagerAdapter {

    private final Context context;
    private final BusDataRepository busDataRepository;

    public BusScheduleFragmentPagerAdapter(Context context, BusDataRepository busDataRepository, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
        this.busDataRepository = busDataRepository;
    }

    @Override
    public Fragment getItem(int position) {
        BusScheduleFragment fragment = BusScheduleFragment.newInstance(Reciprocate.getReciprocate(position));
        new BusSchedulePresenter(busDataRepository, fragment);
        return fragment;
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
