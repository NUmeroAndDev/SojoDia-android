package com.numero.sojodia.model;

public class BusTime {
    public int hour, min, week;
    public boolean isNonstop;

    public BusTime(int hour, int min, int week, boolean isNonstop){
        this.hour = hour;
        this.min = min;
        this.week = week;
        this.isNonstop = isNonstop;
    }
}
