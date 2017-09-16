package com.numero.sojodia.model;

import android.annotation.SuppressLint;

import java.util.Calendar;

public class Time {

    private int hour;
    private int min;
    private int sec;

    public Time() {
        Calendar calendar = Calendar.getInstance();
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.min = calendar.get(Calendar.MINUTE);
        this.sec = calendar.get(Calendar.SECOND);
    }

    public Time(int hour, int min, int sec) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }

    public void setTime(int hour, int min, int sec) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public int getSec() {
        return sec;
    }

    public String toString() {
        return toString(true);
    }

    @SuppressLint("DefaultLocale")
    public String toString(boolean isShowSec) {
        if (isShowSec) {
            return String.format("%02d:%02d:%02d", hour, min, sec);
        }
        return String.format("%02d:%02d", hour, min);
    }
}
