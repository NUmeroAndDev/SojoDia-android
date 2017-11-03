package com.numero.sojodia.view.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
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

    public BusTimePagerAdapter() {
    }

    public void setBusTimeList(List<BusTime> busTimeList) {
        this.busTimeList = busTimeList;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_bus_time, null);

        if (position >= busTimeList.size()) {
            return view;
        }

        TextView busTextView = view.findViewById(R.id.bus_time_text);
        TextView busDescriptionTextView = view.findViewById(R.id.bus_description_text);

        BusTime busTime = busTimeList.get(position);
        busTextView.setText(String.format("%02d:%02d", busTime.hour, busTime.min));
        if (busTime.isNonstop) {
            busDescriptionTextView.setVisibility(View.VISIBLE);
            busDescriptionTextView.setText(R.string.bus_type_nonstop);
        } else {
            busDescriptionTextView.setVisibility(View.GONE);
        }

        View nextBusLayout = view.findViewById(R.id.next_bus_layout);
        TextView nextBusTextView = view.findViewById(R.id.next_bus_time_text);
        TextView nextDescriptionBusTextView = view.findViewById(R.id.next_bus_description_text);

        if (position == busTimeList.size() - 1) {
            // 最終バス
            busDescriptionTextView.setVisibility(View.VISIBLE);
            busDescriptionTextView.setText(R.string.last_bus);

            nextBusLayout.setVisibility(View.GONE);
        } else {
            nextBusLayout.setVisibility(View.VISIBLE);
            BusTime nextBusTime = busTimeList.get(position + 1);
            nextBusTextView.setText(String.format("%02d:%02d", nextBusTime.hour, nextBusTime.min));
            if (nextBusTime.isNonstop) {
                nextDescriptionBusTextView.setVisibility(View.VISIBLE);
                nextDescriptionBusTextView.setText(R.string.bus_type_nonstop);
            } else {
                nextDescriptionBusTextView.setVisibility(View.GONE);
            }
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
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
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
