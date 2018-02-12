package com.numero.sojodia.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.numero.sojodia.model.Route;
import com.numero.sojodia.repository.BusDataRepository;
import com.numero.sojodia.view.adapter.TimeTableRowAdapter;
import com.numero.sojodia.model.BusTime;
import com.numero.sojodia.model.Reciprocate;
import com.numero.sojodia.model.TimeTableRow;
import com.numero.sojodia.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class TimeTableDialog extends ContextWrapper {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private TimeTableRowAdapter adapter;
    private Toolbar toolbar;

    private List<TimeTableRow> timeTableRowList = new ArrayList<>();
    private List<BusTime> busTimeListOnWeekday = new ArrayList<>();
    private List<BusTime> busTimeListOnSaturday = new ArrayList<>();
    private List<BusTime> busTimeListOnSunday = new ArrayList<>();
    private BusDataRepository busDataRepository;
    private Reciprocate reciprocate;
    private Route route;

    public TimeTableDialog(Context context, BusDataRepository repository) {
        super(context);
        this.busDataRepository = repository;

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_time_table, null);
        builder = new AlertDialog.Builder(context);
        builder.setView(view);

        initToolbar(view);
        initListView(view);
    }

    public void setRoute(Route route) {
        this.route = route;
        toolbar.setTitle(route.getStationStringRes());
    }

    public void setReciprocate(Reciprocate reciprocate) {
        this.reciprocate = reciprocate;
        toolbar.setSubtitle(reciprocate.getTitleStringRes());
    }

    public void show() {
        if (route == null) {
            return;
        }
        if (route.equals(Route.TK)) {
            setupTkBusTimeList();
        } else {
            setupTndBusTimeList();
        }
        dialog = builder.show();
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        buildRow();
    }

    private void setupTkBusTimeList() {
        List<BusTime> list = reciprocate.equals(Reciprocate.GOING) ? busDataRepository.getTkBusTimeListGoing() : busDataRepository.getTkBusTimeListReturn();
        Observable.fromIterable(list)
                .subscribe(busTime -> {
                    switch (busTime.getWeek()) {
                        case WEEKDAY:
                            busTimeListOnWeekday.add(busTime);
                            break;
                        case SATURDAY:
                            busTimeListOnSaturday.add(busTime);
                            break;
                        case SUNDAY:
                            busTimeListOnSunday.add(busTime);
                            break;
                    }
                });
    }

    private void setupTndBusTimeList() {
        List<BusTime> list = reciprocate.equals(Reciprocate.GOING) ? busDataRepository.getTndBusTimeListGoing() : busDataRepository.getTndBusTimeListReturn();
        Observable.fromIterable(list)
                .subscribe(busTime -> {
                    switch (busTime.getWeek()) {
                        case WEEKDAY:
                            busTimeListOnWeekday.add(busTime);
                            break;
                        case SATURDAY:
                            busTimeListOnSaturday.add(busTime);
                            break;
                        case SUNDAY:
                            busTimeListOnSunday.add(busTime);
                            break;
                    }
                });
    }

    private void initToolbar(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            if (dialog != null) {
                dialog.dismiss();
            }
        });
    }

    private void initListView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter = new TimeTableRowAdapter(timeTableRowList);
        recyclerView.setAdapter(adapter);
    }

    private void buildRow() {
        // バスは6時から23時しか動いていない
        for (int hour = 6; hour < 24; hour++) {
            TimeTableRow row = new TimeTableRow(hour);
            timeTableRowList.add(row);
        }
        for (BusTime busTime : busTimeListOnWeekday) {
            TimeTableRow row = timeTableRowList.get(busTime.getHour() - 6);
            row.addBusTimeOnWeekday(busTime);
        }
        for (BusTime busTime : busTimeListOnSaturday) {
            TimeTableRow row = timeTableRowList.get(busTime.getHour() - 6);
            row.addBusTimeOnSaturday(busTime);
        }
        for (BusTime busTime : busTimeListOnSunday) {
            TimeTableRow row = timeTableRowList.get(busTime.getHour() - 6);
            row.addBusTimeOnSunday(busTime);
        }
        adapter.notifyDataSetChanged();
    }

}
