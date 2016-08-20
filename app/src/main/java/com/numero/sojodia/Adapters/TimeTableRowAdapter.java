package com.numero.sojodia.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numero.sojodia.R;
import com.numero.sojodia.Models.TimeTableRow;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.time_table_row,viewGroup,false);
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
            holder.hourTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_table_row_highlight));
            holder.timeWeekdayTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_table_row_highlight));
            holder.timeSaturdayTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_table_row_highlight));
            holder.timeSundayTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_table_row_highlight));
            holder.timeHolidayInSchoolTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_table_row_highlight));
        }else{
            holder.hourTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_table_row));
            holder.timeWeekdayTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_table_row));
            holder.timeSaturdayTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_table_row));
            holder.timeSundayTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_table_row));
            holder.timeHolidayInSchoolTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_table_row));
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
