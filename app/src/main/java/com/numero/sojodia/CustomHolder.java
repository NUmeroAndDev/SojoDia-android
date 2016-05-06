package com.numero.sojodia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomHolder extends RecyclerView.ViewHolder{
    TextView time, timeWeekday, timeSaturday, timeSunday, timeSundaySp;
    ArrayList<TableFormat> list;
    Context context;

    CustomHolder(View itemView ,ArrayList<TableFormat> list ,Context context) {
        super(itemView);

        this.list = list;
        this.context = context;

        time = (TextView)itemView.findViewById(R.id.time);
        timeWeekday = (TextView)itemView.findViewById(R.id.week_weekday);
        timeSaturday = (TextView)itemView.findViewById(R.id.week_saturday);
        timeSunday = (TextView)itemView.findViewById(R.id.week_sunday);
        timeSundaySp = (TextView)itemView.findViewById(R.id.week_sunday_sp);
    }
}
