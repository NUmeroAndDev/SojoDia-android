package com.numero.sojodia.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.numero.sojodia.R;
import com.numero.sojodia.model.BusTime;

import java.util.List;

public class BusTimePagerAdapter extends PagerAdapter {

    private List<BusTime> busTimeList;

    public BusTimePagerAdapter setBusTimeList(List<BusTime> busTimeList) {
        this.busTimeList = busTimeList;
        return this;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_bus_time, null);
        TextView textView = (TextView) view.findViewById(R.id.time_text);
        textView.setText(String.format("%d:%d", busTimeList.get(position).hour, busTimeList.get(position).min));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (busTimeList == null) {
            return 0;
        }
        return busTimeList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
