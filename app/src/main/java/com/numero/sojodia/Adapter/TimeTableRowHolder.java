package com.numero.sojodia.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.numero.sojodia.R;

public class TimeTableRowHolder extends RecyclerView.ViewHolder {
    TextView hourTextView, timeWeekdayTextView, timeSaturdayTextView, timeSundayTextView, timeHolidayInSchoolTextView;

    public TimeTableRowHolder(View view) {
        super(view);

        hourTextView = (TextView) view.findViewById(R.id.time);
        timeWeekdayTextView = (TextView) view.findViewById(R.id.week_weekday);
        timeSaturdayTextView = (TextView) view.findViewById(R.id.week_saturday);
        timeSundayTextView = (TextView) view.findViewById(R.id.week_sunday);
//        ToDo: refactor res ID
        timeHolidayInSchoolTextView = (TextView) view.findViewById(R.id.week_sunday_sp);
    }
}
