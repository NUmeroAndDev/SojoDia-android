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

import com.numero.sojodia.model.BusDataFile;
import com.numero.sojodia.model.Route;
import com.numero.sojodia.repository.BusDataRepository;
import com.numero.sojodia.util.DateUtil;
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
    private List<BusTime> busTimeListOnWeekday;
    private List<BusTime> busTimeListOnSaturday;
    private List<BusTime> busTimeListOnSunday;
    private BusDataRepository busDataRepository;
    private Reciprocate reciprocate;
    private Route route;

    private List<BusTime> tkBusTimeListGoing = new ArrayList<>();
    private List<BusTime> tkBusTimeListReturn = new ArrayList<>();
    private List<BusTime> tndBusTimeListGoing = new ArrayList<>();
    private List<BusTime> tndBusTimeListReturn = new ArrayList<>();

    public TimeTableDialog(Context context, BusDataRepository repository) {
        super(context);
        this.busDataRepository = repository;

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_time_table, null);
        builder = new AlertDialog.Builder(context);
        builder.setView(view);

        initToolbar(view);
        initListView(view);

        initBusDataList();
    }

    private void initBusDataList() {
        tkBusTimeListGoing = busDataRepository.loadBusData(BusDataFile.TK_TO_KUTC).blockingFirst();
        tkBusTimeListReturn = busDataRepository.loadBusData(BusDataFile.KUTC_TO_TK).blockingFirst();
        tndBusTimeListGoing = busDataRepository.loadBusData(BusDataFile.TND_TO_KUTC).blockingFirst();
        tndBusTimeListReturn = busDataRepository.loadBusData(BusDataFile.KUTC_TO_TND).blockingFirst();
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
        // FIXME 非効率
        if (reciprocate.equals(Reciprocate.GOING)) {
            this.busTimeListOnWeekday = Observable.fromIterable(tkBusTimeListGoing).filter(busTime -> busTime.week == DateUtil.WEEKDAY).toList().blockingGet();
            this.busTimeListOnSaturday = Observable.fromIterable(tkBusTimeListGoing).filter(busTime -> busTime.week == DateUtil.SATURDAY).toList().blockingGet();
            this.busTimeListOnSunday = Observable.fromIterable(tkBusTimeListGoing).filter(busTime -> busTime.week == DateUtil.SUNDAY).toList().blockingGet();
        } else {
            this.busTimeListOnWeekday = Observable.fromIterable(tkBusTimeListReturn).filter(busTime -> busTime.week == DateUtil.WEEKDAY).toList().blockingGet();
            this.busTimeListOnSaturday = Observable.fromIterable(tkBusTimeListReturn).filter(busTime -> busTime.week == DateUtil.SATURDAY).toList().blockingGet();
            this.busTimeListOnSunday = Observable.fromIterable(tkBusTimeListReturn).filter(busTime -> busTime.week == DateUtil.SUNDAY).toList().blockingGet();
        }
    }

    private void setupTndBusTimeList() {
        if (reciprocate.equals(Reciprocate.GOING)) {
            this.busTimeListOnWeekday = Observable.fromIterable(tndBusTimeListGoing).filter(busTime -> busTime.week == DateUtil.WEEKDAY).toList().blockingGet();
            this.busTimeListOnSaturday = Observable.fromIterable(tndBusTimeListGoing).filter(busTime -> busTime.week == DateUtil.SATURDAY).toList().blockingGet();
            this.busTimeListOnSunday = Observable.fromIterable(tndBusTimeListGoing).filter(busTime -> busTime.week == DateUtil.SUNDAY).toList().blockingGet();
        } else {
            this.busTimeListOnWeekday = Observable.fromIterable(tndBusTimeListReturn).filter(busTime -> busTime.week == DateUtil.WEEKDAY).toList().blockingGet();
            this.busTimeListOnSaturday = Observable.fromIterable(tndBusTimeListReturn).filter(busTime -> busTime.week == DateUtil.SATURDAY).toList().blockingGet();
            this.busTimeListOnSunday = Observable.fromIterable(tndBusTimeListReturn).filter(busTime -> busTime.week == DateUtil.SUNDAY).toList().blockingGet();
        }
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
            TimeTableRow row = new TimeTableRow();
            row.setHour(hour);
            timeTableRowList.add(row);
        }
        if (busTimeListOnWeekday != null) {
            for (BusTime busTime : busTimeListOnWeekday) {
                TimeTableRow row = timeTableRowList.get(busTime.hour - 6);
                row.addTimeOnWeekday(busTime.min, busTime.isNonstop);
            }
        }
        if (busTimeListOnSaturday != null) {
            for (BusTime busTime : busTimeListOnSaturday) {
                TimeTableRow row = timeTableRowList.get(busTime.hour - 6);
                row.addTimeOnSaturday(busTime.min, busTime.isNonstop);
            }
        }

        if (busTimeListOnSunday != null) {
            for (BusTime busTime : busTimeListOnSunday) {
                TimeTableRow row = timeTableRowList.get(busTime.hour - 6);
                row.addTimeOnSunday(busTime.min, busTime.isNonstop);
            }
        }
        adapter.notifyDataSetChanged();
    }

}
