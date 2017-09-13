package com.numero.sojodia.manager;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;

import com.numero.sojodia.model.BusDataFile;
import com.numero.sojodia.model.BusTime;
import com.numero.sojodia.model.Reciprocate;
import com.numero.sojodia.model.Time;
import com.numero.sojodia.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;

public class BusDataManager extends ContextWrapper {

    private static final int NO_BUS_POSITION = -1;

    private List<BusTime> currentTkTimeList = new ArrayList<>();
    private List<BusTime> currentTndTimeList = new ArrayList<>();

    private List<BusTime> tkBusTimeListGoing = new ArrayList<>();
    private List<BusTime> tkBusTimeListReturn = new ArrayList<>();
    private List<BusTime> tndBusTimeListGoing = new ArrayList<>();
    private List<BusTime> tndBusTimeListReturn = new ArrayList<>();

    private DataManager dataManager;
    private int week = -1;

    private int tkBusPosition = 0;
    private int tndBusPosition = 0;

    public static BusDataManager getInstance(@NonNull Context context) {
        return new BusDataManager(context);
    }

    public BusDataManager(Context context) {
        super(context);

        dataManager = DataManager.getInstance(context);

        initBusTimeList();
    }

    public void setWeekAndReciprocate(int week, Reciprocate reciprocate) {
        this.week = week;

        switch (reciprocate) {
            case GOING:
                currentTkTimeList = getTkGoingBusTimeList(week);
                currentTndTimeList = getTndGoingBusTimeList(week);
                break;
            case RETURN:
                currentTkTimeList = getTkReturnBusTimeList(week);
                currentTndTimeList = getTndReturnBusTimeList(week);
                break;
        }
        initBusPosition();
    }

    public List<BusTime> getTkTimeList() {
        return currentTkTimeList;
    }

    public List<BusTime> getTndTimeList() {
        return currentTndTimeList;
    }

    private void initBusTimeList() {
        setBusTimeList(tkBusTimeListGoing, dataManager.getBusTimeDataSource(BusDataFile.TK_TO_KUTC.getFileName()));
        setBusTimeList(tkBusTimeListReturn, dataManager.getBusTimeDataSource(BusDataFile.KUTC_TO_TK.getFileName()));
        setBusTimeList(tndBusTimeListGoing, dataManager.getBusTimeDataSource(BusDataFile.TND_TO_KUTC.getFileName()));
        setBusTimeList(tndBusTimeListReturn, dataManager.getBusTimeDataSource(BusDataFile.KUTC_TO_TND.getFileName()));
    }

    private void initBusPosition() {
        tkBusPosition = findBusPosition(currentTkTimeList);
        tndBusPosition = findBusPosition(currentTndTimeList);
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

    public int getTkBusPosition() {
        return tkBusPosition;
    }

    public int getTndBusPosition() {
        return tndBusPosition;
    }

    public void previewTkBusTime() {
        tkBusPosition -= 1;
    }

    public void nextTkBusTime() {
        tkBusPosition += 1;
        if (tkBusPosition == currentTkTimeList.size()) {
            tkBusPosition = NO_BUS_POSITION;
        }
    }

    public boolean canPreviewTkTime() {
        if (tkBusPosition == NO_BUS_POSITION || tkBusPosition == 0) {
            return false;
        }
        BusTime busTime = currentTkTimeList.get(tkBusPosition - 1);
        Time time = new Time();
        Time currentTime = TimeUtil.initCurrentTime();
        time.setTime(busTime.hour, busTime.min, 0);
        return TimeUtil.isOverTime(currentTime, time);
    }

    public boolean canNextTkTime() {
        return tkBusPosition != NO_BUS_POSITION && !(tkBusPosition + 1 >= currentTkTimeList.size());
    }

    public boolean isNoTkBus() {
        return tkBusPosition == NO_BUS_POSITION;
    }

    public void previewTndBusTime() {
        tndBusPosition -= 1;
    }

    public void nextTndBusTime() {
        tndBusPosition += 1;
        if (tndBusPosition == currentTndTimeList.size()) {
            tndBusPosition = NO_BUS_POSITION;
        }
    }

    public boolean canPreviewTndTime() {
        if (tndBusPosition == NO_BUS_POSITION || tndBusPosition == 0) {
            return false;
        }
        BusTime busTime = currentTndTimeList.get(tndBusPosition - 1);
        Time time = new Time();
        Time currentTime = TimeUtil.initCurrentTime();
        time.setTime(busTime.hour, busTime.min, 0);
        return TimeUtil.isOverTime(currentTime, time);
    }

    public boolean canNextTndTime() {
        return tndBusPosition != NO_BUS_POSITION && !(tndBusPosition + 1 >= currentTndTimeList.size());
    }

    public boolean isNoTndBus() {
        return tndBusPosition == NO_BUS_POSITION;
    }

    private int findBusPosition(List<BusTime> busTimeList) {
        int position;
        Time time = new Time();
        Time currentTime = TimeUtil.initCurrentTime();
        for (position = 0; position < busTimeList.size(); position++) {
            time.setTime(busTimeList.get(position).hour, busTimeList.get(position).min, 0);
            if (TimeUtil.isOverTime(currentTime, time)) {
                return position;
            }
        }
        return NO_BUS_POSITION;
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
