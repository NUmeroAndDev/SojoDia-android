package com.numero.sojodia.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.numero.sojodia.view.adapter.TimeTableRowAdapter;
import com.numero.sojodia.model.BusTime;
import com.numero.sojodia.model.Reciprocate;
import com.numero.sojodia.model.TimeTableRow;
import com.numero.sojodia.R;

import java.util.ArrayList;
import java.util.List;

public class TimeTableDialog {

    private Context context;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private List<TimeTableRow> timeTableRowList = new ArrayList<>();
    private List<BusTime> busTimeListOnWeekday;
    private List<BusTime> busTimeListOnSaturday;
    private List<BusTime> busTimeListOnSunday;
    private TimeTableRowAdapter adapter;
    private View view;

    TimeTableDialog(Context context) {
        this.context = context;

        view = LayoutInflater.from(context).inflate(R.layout.dialog_time_table, null);
        builder = new AlertDialog.Builder(context);
        builder.setView(view);

        initToolbar();
        initListView();
    }

    public static TimeTableDialog init(Context context) {
        return new TimeTableDialog(context);
    }

    public TimeTableDialog setBusTimeListOnWeekday(List<BusTime> busTimeList) {
        this.busTimeListOnWeekday = busTimeList;
        return this;
    }

    public TimeTableDialog setBusTimeListOnSaturday(List<BusTime> busTimeList) {
        this.busTimeListOnSaturday = busTimeList;
        return this;
    }

    public TimeTableDialog setBusTimeListOnSunday(List<BusTime> busTimeList) {
        this.busTimeListOnSunday = busTimeList;
        return this;
    }

    public TimeTableDialog setRouteTk() {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.station_tk);
        return this;
    }

    public TimeTableDialog setRouteTnd() {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.station_tnd);
        return this;
    }

    public TimeTableDialog setReciprocate(Reciprocate reciprocate) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setSubtitle(reciprocate.getTitleStringRes());
        return this;
    }

    public void show() {
        dialog = builder.show();
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        buildRow();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
    }

    private void initListView() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TimeTableRowAdapter(timeTableRowList);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("DefaultLocale")
    private void buildRow() {
        for (int hour = 6; hour < 24; hour++) {
            TimeTableRow row = new TimeTableRow();
            row.setHourText(String.format("%02d", hour));
            timeTableRowList.add(row);
        }
        String busType = "";
        if (busTimeListOnWeekday != null) {
            for (int i = 0; i < busTimeListOnWeekday.size(); i++) {
                TimeTableRow row = timeTableRowList.get(busTimeListOnWeekday.get(i).hour - 6);
                busType = busTimeListOnWeekday.get(i).isNonstop ? "直" : "";
                row.addTimeTextOnWeekday(String.format("%s%02d", busType, busTimeListOnWeekday.get(i).min));
            }
        }
        if (busTimeListOnSaturday != null) {
            for (int i = 0; i < busTimeListOnSaturday.size(); i++) {
                TimeTableRow row = timeTableRowList.get(busTimeListOnSaturday.get(i).hour - 6);
                busType = busTimeListOnSaturday.get(i).isNonstop ? "直" : "";
                row.addTimeTextOnSaturday(String.format("%s%02d", busType, busTimeListOnSaturday.get(i).min));
            }
        }

        if (busTimeListOnSunday != null) {
            for (int i = 0; i < busTimeListOnSunday.size(); i++) {
                TimeTableRow row = timeTableRowList.get(busTimeListOnSunday.get(i).hour - 6);
                busType = busTimeListOnSunday.get(i).isNonstop ? "直" : "";
                row.addTimeTextOnSunday(String.format("%s%02d", busType, busTimeListOnSunday.get(i).min));
            }
        }
        adapter.notifyDataSetChanged();
    }

}
