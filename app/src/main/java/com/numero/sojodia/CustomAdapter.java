package com.numero.sojodia;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomAdapter extends RecyclerView.Adapter<CustomHolder> {
    ArrayList<TableFormat> list;
    Context context;

    CustomAdapter(ArrayList<TableFormat> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public CustomHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout,viewGroup,false);
        return new CustomHolder(view, list, context);
    }

    @Override
    public void onBindViewHolder(final CustomHolder holder, int i) {
        holder.time.setText(list.get(i).time);
        holder.timeWeekday.setText(list.get(i).timeWeekday);
        holder.timeSaturday.setText(list.get(i).timeSaturday);
        holder.timeSunday.setText(list.get(i).timeSunday);
        holder.timeSundaySp.setText(list.get(i).timeSundaySp);
        if(i == (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) - 6)){
            holder.time.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTimeTableNowTime));
            holder.timeWeekday.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTimeTableNowTime));
            holder.timeSaturday.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTimeTableNowTime));
            holder.timeSunday.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTimeTableNowTime));
            holder.timeSundaySp.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTimeTableNowTime));
        }else{
            holder.time.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.timeWeekday.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.timeSaturday.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.timeSunday.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            holder.timeSundaySp.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }else{
            return 0;
        }
    }
}
