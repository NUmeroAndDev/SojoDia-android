package com.numero.sojodia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numero.sojodia.R;
import com.numero.sojodia.model.TimeTableRow;

import java.util.Calendar;
import java.util.List;

public class TimeTableRowAdapter extends RecyclerView.Adapter<TimeTableRowHolder> {

    private List<TimeTableRow> rowList;

    public TimeTableRowAdapter(List<TimeTableRow> rowLIst) {
        this.rowList = rowLIst;
    }

    @Override
    public TimeTableRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.holder_time_table_row, viewGroup, false);
        return new TimeTableRowHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeTableRowHolder holder, int position) {
        holder.setTimeTableRow(rowList.get(position));
        holder.setIsCurrentHour(position == (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) - 6));
    }

    @Override
    public int getItemCount() {
        if (rowList == null) {
            return 0;
        }
        return rowList.size();
    }
}
