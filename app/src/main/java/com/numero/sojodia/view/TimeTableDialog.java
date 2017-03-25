package com.numero.sojodia.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.numero.sojodia.adapter.TimeTableRowAdapter;
import com.numero.sojodia.manager.BusDataManager;
import com.numero.sojodia.model.BusTime;
import com.numero.sojodia.model.TimeTableRow;
import com.numero.sojodia.R;
import com.numero.sojodia.util.Constants;

import java.util.ArrayList;

public class TimeTableDialog {

    private Context context;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private ArrayList<TimeTableRow> rows = new ArrayList<>();
    private ArrayList<BusTime> busTimeList = new ArrayList<>();
    private TimeTableRowAdapter adapter;
    private View view;
    private int route, reciprocating;

    TimeTableDialog(Context context, int route, int reciprocating) {
        this.context = context;
        this.route = route;
        this.reciprocating = reciprocating;

        view = LayoutInflater.from(context).inflate(R.layout.time_table_dialog, null);
        builder = new AlertDialog.Builder(context);
        builder.setView(view);

        initToolbar();
        initListView();
    }

    public static TimeTableDialog init(Context context, int route, int reciprocating) {
        return new TimeTableDialog(context, route, reciprocating);
    }

    public void show() {
        dialog = builder.show();
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        new CreateTimeTableTask().execute();
    }

    private void initToolbar() {
        int stationStringRes[] = {R.string.station_tk, R.string.station_tnd};
        int reciprocatingStringRes[] = {R.string.going_to_school, R.string.coming_home};

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(stationStringRes[route]);
        toolbar.setSubtitle(reciprocatingStringRes[reciprocating]);
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
        adapter = new TimeTableRowAdapter(rows);
        recyclerView.setAdapter(adapter);
    }

    private class CreateTimeTableTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            switch (route) {
                case Constants.ROUTE_TK:
                    BusDataManager.initBusDataTK(context, busTimeList, reciprocating, BusDataManager.ALL);
                    break;
                case Constants.ROUTE_TND:
                    BusDataManager.initBusDataTND(context, busTimeList, reciprocating, BusDataManager.ALL);
            }
            setRowItem();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
        }

        @SuppressLint("DefaultLocale")
        private void setRowItem() {
            for (int hour = 6; hour < 24; hour++) {
                TimeTableRow row = new TimeTableRow();
                row.setHourText(String.format("%02d", hour));
                rows.add(row);
            }
            for (int i = 0; i < busTimeList.size(); i++) {
                TimeTableRow row = rows.get(busTimeList.get(i).hour - 6);
                switch (busTimeList.get(i).week) {
                    case BusDataManager.WEEKDAY:
                        row.addTimeTextOnWeekday(String.format("%02d", busTimeList.get(i).min));
                        break;
                    case BusDataManager.SATURDAY:
                        row.addTimeTextOnSaturday(String.format("%02d", busTimeList.get(i).min));
                        break;
                    case BusDataManager.SUNDAY:
                        row.addTimeTextOnSunday(String.format("%02d", busTimeList.get(i).min));
                        break;
                    case BusDataManager.HOLIDAY_IN_SCHOOL:
                        row.addTimeTextOnHolidayInSchool(String.format("%02d", busTimeList.get(i).min));
                        break;
                }
            }
        }
    }

}
