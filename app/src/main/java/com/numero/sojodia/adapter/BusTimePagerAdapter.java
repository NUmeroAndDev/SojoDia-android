package com.numero.sojodia.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
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
    private Resources resources;

    public BusTimePagerAdapter(Context context) {
        resources = context.getResources();
    }

    public BusTimePagerAdapter setBusTimeList(List<BusTime> busTimeList) {
        this.busTimeList = busTimeList;
        return this;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_bus_time, null);

        TextView busTextView = (TextView) view.findViewById(R.id.bus_time_text);

        busTextView.setText(String.format("%s\n%02d:%02d", getBusType(position), busTimeList.get(position).hour, busTimeList.get(position).min));

        TextView nextBusTextView = (TextView) view.findViewById(R.id.next_bus_time_text);
        if (position == busTimeList.size() - 1) {
            nextBusTextView.setText(R.string.last_bus);
        } else {
            nextBusTextView.setText(String.format("Next\n%s\n%02d:%02d", getBusType(position + 1), busTimeList.get(position + 1).hour, busTimeList.get(position + 1).min));
        }

        container.addView(view);
        return view;
    }

    private String getBusType(int position) {
        if (busTimeList.get(position).isNonstop) {
            return resources.getString(R.string.bus_type_nonstop);
        }  else {
            return resources.getString(R.string.bus_type_regular);
        }
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
