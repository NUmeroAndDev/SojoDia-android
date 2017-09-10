package com.numero.sojodia.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.numero.sojodia.R;
import com.numero.sojodia.model.TimeTableRow;

public class TimeTableRowHolder extends RecyclerView.ViewHolder {

    public TimeTableRowHolder(View view) {
        super(view);
    }

    public void setTimeTableRow(TimeTableRow row) {
        setHourText(row.hourText);
        setTimeTextOnWeekday(row.timeTextOnWeekday);
        setTimeTextOnSaturday(row.timeTextOnSaturday);
        setTimeTextOnSunday(row.timeTextOnSunday);
    }

    public void setIsCurrentHour(boolean isCurrentHour) {
        View view = itemView.findViewById(R.id.row_layout);
        Context context = itemView.getContext();
        if (isCurrentHour) {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.time_table_dialog_row_highlight));
        } else {
            view.setBackground(null);
        }
    }

    private void setHourText(String text) {
        TextView textView = (TextView) itemView.findViewById(R.id.hour_text);
        textView.setText(text);
    }

    private void setTimeTextOnWeekday(String text) {
        TextView textView = (TextView) itemView.findViewById(R.id.weekday_text);
        textView.setText(text);
    }

    private void setTimeTextOnSaturday(String text) {
        TextView textView = (TextView) itemView.findViewById(R.id.saturday_text);
        textView.setText(text);
    }

    private void setTimeTextOnSunday(String text) {
        TextView textView = (TextView) itemView.findViewById(R.id.sunday_text);
        textView.setText(text);
    }

}
