package com.numero.sojodia.presenter;

import com.numero.sojodia.contract.BusScheduleContract;
import com.numero.sojodia.model.BusDataFile;
import com.numero.sojodia.model.BusTime;
import com.numero.sojodia.model.Reciprocate;
import com.numero.sojodia.model.Time;
import com.numero.sojodia.repository.BusDataRepository;
import com.numero.sojodia.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class BusSchedulePresenter implements BusScheduleContract.Presenter {

    private static final int NO_BUS_POSITION = -1;

    private final BusDataRepository busDataRepository;

    private final BusScheduleContract.View view;
    private final Reciprocate reciprocate;
    private int week = 0;

    private int tkBusPosition = 0;
    private int tndBusPosition = 0;

    private List<BusTime> tkBusTimeList = new ArrayList<>();
    private List<BusTime> tndBusTimeList = new ArrayList<>();

    private List<BusTime> tkBusTimeListGoing = new ArrayList<>();
    private List<BusTime> tkBusTimeListReturn = new ArrayList<>();
    private List<BusTime> tndBusTimeListGoing = new ArrayList<>();
    private List<BusTime> tndBusTimeListReturn = new ArrayList<>();

    public BusSchedulePresenter(BusScheduleContract.View view, BusDataRepository busDataRepository, Reciprocate reciprocate) {
        this.view = view;
        this.busDataRepository = busDataRepository;
        this.reciprocate = reciprocate;

        this.view.setPresenter(this);

        initBusDataList();
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unSubscribe() {
    }

    @Override
    public void onTimeChanged(int week) {
        this.week = week;
        tkBusTimeList.clear();
        tndBusTimeList.clear();
        view.showTkBusTimeList(getTkTimeList());
        view.showTndBusTimeList(getTndTimeList());

        tkBusPosition = findBusPosition(getTkTimeList());
        tndBusPosition = findBusPosition(getTndTimeList());

        if (tkBusPosition == NO_BUS_POSITION) {
            view.showTkNoBusLayout();
            view.hideTkNextButton();
            view.hideTkPreviewButton();
        } else {
            view.hideTkNoBusLayout();
            view.selectTkCurrentBusPosition(tkBusPosition);
            view.startTkCountDown(getTkTimeList().get(tkBusPosition));
            if (tkBusPosition >= getTkTimeList().size() - 1) {
                view.hideTkNextButton();
            } else {
                view.showTkNextButton();
            }
            if (canPreviewTkTime()) {
                view.showTkPreviewButton();
            } else {
                view.hideTkPreviewButton();
            }
        }
        if (tndBusPosition == NO_BUS_POSITION) {
            view.showTndNoBusLayout();
            view.hideTndNextButton();
            view.hideTndPreviewButton();
        } else {
            view.hideTndNoBusLayout();
            view.selectTndCurrentBusPosition(tndBusPosition);
            view.startTndCountDown(getTndTimeList().get(tndBusPosition));
            if (tndBusPosition >= getTndTimeList().size() - 1) {
                view.hideTndNextButton();
            } else {
                view.showTndNextButton();
            }
            if (canPreviewTndTime()) {
                view.showTndPreviewButton();
            } else {
                view.hideTndPreviewButton();
            }
        }
    }

    @Override
    public void nextTkBus() {
        if (tkBusPosition + 1 == getTkTimeList().size()) {
            tkBusPosition = NO_BUS_POSITION;
        }
        if (tkBusPosition == NO_BUS_POSITION) {
            view.hideTkNextButton();
            view.hideTkPreviewButton();
            view.showTkNoBusLayout();
            return;
        }
        tkBusPosition += 1;
        if (tkBusPosition >= getTkTimeList().size() - 1) {
            view.hideTkNextButton();
        } else {
            view.showTkNextButton();
        }
        if (canPreviewTkTime()) {
            view.showTkPreviewButton();
        } else {
            view.hideTkPreviewButton();
        }
        view.selectTkCurrentBusPosition(tkBusPosition);
        view.startTkCountDown(getTkTimeList().get(tkBusPosition));
    }

    @Override
    public void previewTkBus() {
        tkBusPosition -= 1;
        view.selectTkCurrentBusPosition(tkBusPosition);
        view.startTkCountDown(getTkTimeList().get(tkBusPosition));
        if (canPreviewTkTime()) {
            view.showTkPreviewButton();
        } else {
            view.hideTkPreviewButton();
        }
    }

    @Override
    public void nextTndBus() {
        if (tndBusPosition + 1 == getTndTimeList().size()) {
            tndBusPosition = NO_BUS_POSITION;
        }
        if (tndBusPosition == NO_BUS_POSITION) {
            view.hideTndNextButton();
            view.hideTndPreviewButton();
            view.showTndNoBusLayout();
            return;
        }
        tndBusPosition += 1;
        if (tndBusPosition >= getTndTimeList().size() - 1) {
            view.hideTndNextButton();
        } else {
            view.showTndNextButton();
        }
        if (canPreviewTndTime()) {
            view.showTndPreviewButton();
        } else {
            view.hideTndPreviewButton();
        }
        view.selectTndCurrentBusPosition(tndBusPosition);
        view.startTndCountDown(getTndTimeList().get(tndBusPosition));
    }

    @Override
    public void previewTndBus() {
        tndBusPosition -= 1;
        view.selectTndCurrentBusPosition(tndBusPosition);
        view.startTndCountDown(getTndTimeList().get(tndBusPosition));
        if (canPreviewTndTime()) {
            view.showTndPreviewButton();
        } else {
            view.hideTndPreviewButton();
        }
    }

    private List<BusTime> getTkTimeList() {
        if (tkBusTimeList.isEmpty()) {
            tkBusTimeList = getTkBusTimeListObservable().blockingGet();
        }
        return tkBusTimeList;
    }

    private List<BusTime> getTndTimeList() {
        if (tndBusTimeList.isEmpty()) {
            tndBusTimeList = getTndBusTimeListObservable().blockingGet();
        }
        return tndBusTimeList;
    }

    private void initBusDataList() {
        tkBusTimeListGoing = busDataRepository.loadBusData(BusDataFile.TK_TO_KUTC).blockingFirst();
        tkBusTimeListReturn = busDataRepository.loadBusData(BusDataFile.KUTC_TO_TK).blockingFirst();
        tndBusTimeListGoing = busDataRepository.loadBusData(BusDataFile.TND_TO_KUTC).blockingFirst();
        tndBusTimeListReturn = busDataRepository.loadBusData(BusDataFile.KUTC_TO_TND).blockingFirst();
    }

    private int findBusPosition(List<BusTime> busTimeList) {
        int position;
        Time time = new Time();
        for (position = 0; position < busTimeList.size(); position++) {
            time.setTime(busTimeList.get(position).hour, busTimeList.get(position).min, 0);
            if (TimeUtil.isOverTime(new Time(), time)) {
                return position;
            }
        }
        return NO_BUS_POSITION;
    }

    private Single<List<BusTime>> getTkBusTimeListObservable() {
        switch (reciprocate) {
            case GOING:
                return Observable.fromIterable(tkBusTimeListGoing)
                        .filter(busTime -> busTime.week == week)
                        .toList();
            case RETURN:
            default:
                return Observable.fromIterable(tkBusTimeListReturn)
                        .filter(busTime -> busTime.week == week)
                        .toList();
        }
    }

    private Single<List<BusTime>> getTndBusTimeListObservable() {
        switch (reciprocate) {
            case GOING:
                return Observable.fromIterable(tndBusTimeListGoing)
                        .filter(busTime -> busTime.week == week)
                        .toList();
            case RETURN:
            default:
                return Observable.fromIterable(tndBusTimeListReturn)
                        .filter(busTime -> busTime.week == week)
                        .toList();
        }
    }

    private boolean canPreviewTkTime() {
        if (tkBusPosition == NO_BUS_POSITION || tkBusPosition == 0) {
            return false;
        }
        BusTime busTime = getTkTimeList().get(tkBusPosition - 1);
        Time time = new Time();
        time.setTime(busTime.hour, busTime.min, 0);
        return TimeUtil.isOverTime(new Time(), time);
    }

    private boolean canPreviewTndTime() {
        if (tndBusPosition == NO_BUS_POSITION || tndBusPosition == 0) {
            return false;
        }
        BusTime busTime = getTndTimeList().get(tndBusPosition - 1);
        Time time = new Time();
        time.setTime(busTime.hour, busTime.min, 0);
        return TimeUtil.isOverTime(new Time(), time);
    }
}
