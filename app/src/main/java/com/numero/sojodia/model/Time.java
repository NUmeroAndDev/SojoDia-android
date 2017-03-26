package com.numero.sojodia.model;

import android.annotation.SuppressLint;

public class Time {
    public int hour, min, sec;

    public Time() {
    }

    public void setTime(int hour, int min, int sec) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
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
