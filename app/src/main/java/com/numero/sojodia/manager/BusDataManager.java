package com.numero.sojodia.manager;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;

import com.numero.sojodia.model.BusTime;

import java.util.ArrayList;
import java.util.List;

public class BusDataManager extends ContextWrapper {

    private List<BusTime> tkBusTimeListOnWeekdayGoing = new ArrayList<>();
    private List<BusTime> tkBusTimeListOnSaturdayGoing = new ArrayList<>();
    private List<BusTime> tkBusTimeListOnSundayGoing = new ArrayList<>();
    private List<BusTime> tkBusTimeListOnWeekdayReturn = new ArrayList<>();
    private List<BusTime> tkBusTimeListOnSaturdayReturn = new ArrayList<>();
    private List<BusTime> tkBusTimeListOnSundayReturn = new ArrayList<>();
    private List<BusTime> tndBusTimeListOnWeekdayGoing = new ArrayList<>();
    private List<BusTime> tndBusTimeListOnSaturdayGoing = new ArrayList<>();
    private List<BusTime> tndBusTimeListOnSundayGoing = new ArrayList<>();
    private List<BusTime> tndBusTimeListOnWeekdayReturn = new ArrayList<>();
    private List<BusTime> tndBusTimeListOnSaturdayReturn = new ArrayList<>();
    private List<BusTime> tndBusTimeListOnSundayReturn = new ArrayList<>();

    private static BusDataManager INSTANCE;

    public static BusDataManager getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new BusDataManager(context);
        }
        return INSTANCE;
    }

    public BusDataManager(Context context) {
        super(context);
    }
}
