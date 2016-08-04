package com.numero.sojodia.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numero.sojodia.R;
import com.numero.sojodia.Model.TimeTableRow;

import java.util.ArrayList;
import java.util.Calendar;

public class TimeTableRowAdapter extends RecyclerView.Adapter<TimeTableRowHolder> {
    private ArrayList<TimeTableRow> rows;
    private Context context;

    public TimeTableRowAdapter(ArrayList<TimeTableRow> rows, Context context){
        this.rows = rows;
        this.context = context;
    }

    @Override
    public TimeTableRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout,viewGroup,false);
        return new TimeTableRowHolder(view);
    }

    @Override
    public void onBindViewHolder(final TimeTableRowHolder holder, int position) {
        holder.hourTextView.setText(rows.get(position).hourString);
        holder.timeWeekdayTextView.setText(rows.get(position).timeWeekday);
        holder.timeSaturdayTextView.setText(rows.get(position).timeSaturday);
        holder.timeSundayTextView.setText(rows.get(position).timeSunday);
        holder.timeHolidayInSchoolTextView.setText(rows.get(position).timeHolidayInSchool);
        if(position == (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) - 6)){
            holder.hourTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTimeTableNowTime));
            holder.timeWeekdayTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTimeTableNowTime));
            holder.timeSaturdayTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTimeTableNowTime));
            holder.timeSundayTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTimeTableNowTime));
            holder.timeHolidayInSchoolTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTimeTableNowTime));
        }else{
            holder.hourTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.timeWeekdayTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.timeSaturdayTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.timeSundayTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.timeHolidayInSchoolTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        if(rows != null){
            return rows.size();
        }else{
            return 0;
        }
    }
}
