package com.numero.sojodia.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.numero.sojodia.R;

public class TimeTableRowHolder extends RecyclerView.ViewHolder {
    TextView hourTextView, timeWeekdayTextView, timeSaturdayTextView, timeSundayTextView, timeHolidayInSchoolTextView;

    public TimeTableRowHolder(View view) {
        super(view);

        hourTextView = (TextView) view.findViewById(R.id.hour);
        timeWeekdayTextView = (TextView) view.findViewById(R.id.weekday);
        timeSaturdayTextView = (TextView) view.findViewById(R.id.saturday);
        timeSundayTextView = (TextView) view.findViewById(R.id.sunday);
        timeHolidayInSchoolTextView = (TextView) view.findViewById(R.id.holiday_in_school);
    }
}
