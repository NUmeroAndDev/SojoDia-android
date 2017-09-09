package com.numero.sojodia.manager;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;

import com.numero.sojodia.adapter.BusScheduleFragmentPagerAdapter;
import com.numero.sojodia.model.BusDataFile;
import com.numero.sojodia.model.BusTime;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

public class BusDataManager extends ContextWrapper {

    private List<BusTime> currentTkTimeList = new ArrayList<>();
    private List<BusTime> currentTndTimeList = new ArrayList<>();

    private List<BusTime> tkBusTimeListGoing = new ArrayList<>();
    private List<BusTime> tkBusTimeListReturn = new ArrayList<>();
    private List<BusTime> tndBusTimeListGoing = new ArrayList<>();
    private List<BusTime> tndBusTimeListReturn = new ArrayList<>();

    private DataManager dataManager;
    private int week = -1;

    public static BusDataManager getInstance(@NonNull Context context) {
        return new BusDataManager(context);
    }

    public BusDataManager(Context context) {
        super(context);

        dataManager = DataManager.getInstance(context);

        initBusTimeList();
    }

    public void setWeekAndReciprocate(int week, int reciprocate) {
        if (this.week == week) {
            return;
        }
        this.week = week;

        if (reciprocate == BusScheduleFragmentPagerAdapter.RECIPROCATE_GOING) {
            currentTkTimeList = getTkGoingBusTimeList(week);
            currentTndTimeList = getTndGoingBusTimeList(week);
        } else {
            currentTkTimeList = getTkReturnBusTimeList(week);
            currentTndTimeList = getTndReturnBusTimeList(week);
        }
    }

    public List<BusTime> getTkTimeList() {
        return currentTkTimeList;
    }

    public List<BusTime> getTndTimeList() {
        return currentTndTimeList;
    }

    public boolean isTkLastBus(int position) {
        return (currentTkTimeList.size() - 1) == position;
    }

    public boolean isTndLastBus(int position) {
        return (currentTndTimeList.size() - 1) == position;
    }

    private void initBusTimeList() {
        setBusTimeList(tkBusTimeListGoing, dataManager.getBusTimeDataSource(BusDataFile.TK_TO_KUTC.getFileName()));
        setBusTimeList(tkBusTimeListReturn, dataManager.getBusTimeDataSource(BusDataFile.KUTC_TO_TK.getFileName()));
        setBusTimeList(tndBusTimeListGoing, dataManager.getBusTimeDataSource(BusDataFile.TND_TO_KUTC.getFileName()));
        setBusTimeList(tndBusTimeListReturn, dataManager.getBusTimeDataSource(BusDataFile.KUTC_TO_TND.getFileName()));
    }

    public List<BusTime> getTkGoingBusTimeList(int week) {
        return getFilteredBusTimeList(tkBusTimeListGoing, week);
    }

    public List<BusTime> getTkReturnBusTimeList(int week) {
        return getFilteredBusTimeList(tkBusTimeListReturn, week);
    }

    public List<BusTime> getTndGoingBusTimeList(int week) {
        return getFilteredBusTimeList(tndBusTimeListGoing, week);
    }

    public List<BusTime> getTndReturnBusTimeList(int week) {
        return getFilteredBusTimeList(tndBusTimeListReturn, week);
    }

    private List<BusTime> getFilteredBusTimeList(List<BusTime> busTimeList, final int week) {
        return Observable.fromIterable(busTimeList)
                .filter(new Predicate<BusTime>() {
                    @Override
                    public boolean test(@io.reactivex.annotations.NonNull BusTime busTime) throws Exception {
                        return busTime.week == week;
                    }
                })
                .toList()
                .blockingGet();
    }

    // テキストからマッピング処理
    private void setBusTimeList(List<BusTime> busTimeList, String busTimeSource) {
        busTimeList.clear();
        String lines[] = busTimeSource.split("\n");
        for (String line : lines) {
            StringTokenizer stringTokenizer = new StringTokenizer(line, ",");

            int hour = Integer.valueOf(stringTokenizer.nextToken());
            int minutes = Integer.valueOf(stringTokenizer.nextToken());
            int week = Integer.valueOf(stringTokenizer.nextToken());
            boolean isNonstop = Integer.valueOf(stringTokenizer.nextToken()) != 0;

            BusTime busTime = new BusTime(hour, minutes, week, isNonstop);
            busTimeList.add(busTime);
        }
    }
}
