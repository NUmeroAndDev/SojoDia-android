package com.numero.sojodia.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.numero.sojodia.R;
import com.numero.sojodia.model.TimeTableRow;

public class TimeTableRowHolder extends RecyclerView.ViewHolder {

    private View rowLayout;
    private TextView hourTextView;
    private TextView weekdayTextView;
    private TextView saturdayTextView;
    private TextView sundayTextView;

    public TimeTableRowHolder(View view) {
        super(view);

        rowLayout = view.findViewById(R.id.row_layout);
        hourTextView = view.findViewById(R.id.hour_text);
        weekdayTextView = view.findViewById(R.id.weekday_text);
        saturdayTextView = view.findViewById(R.id.saturday_text);
        sundayTextView = view.findViewById(R.id.sunday_text);
    }

    public void setTimeTableRow(TimeTableRow row) {
        hourTextView.setText(row.getHourText());
        weekdayTextView.setText(row.getTimeOnWeekdayText());
        saturdayTextView.setText(row.getTimeOnSaturdayText());
        sundayTextView.setText(row.getTimeOnSundayText());
    }

    public void setIsCurrentHour(boolean isCurrentHour) {
        Context context = itemView.getContext();
        if (isCurrentHour) {
            rowLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.time_table_dialog_row_highlight));
        } else {
            rowLayout.setBackground(null);
        }
    }
}
