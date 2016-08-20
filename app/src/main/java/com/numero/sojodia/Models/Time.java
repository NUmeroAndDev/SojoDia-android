package com.numero.sojodia.Models;

import java.util.Calendar;

public class Time {
    public int hour, min, sec;

    public Time() {
    }

    public void setTime(int hour, int min, int sec) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }

    public void setNowTime() {
        this.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        this.min = Calendar.getInstance().get(Calendar.MINUTE);
        this.sec = Calendar.getInstance().get(Calendar.SECOND);
    }

    public static Time addition(Time before, Time after) {
        Time time = new Time();
        time.sec = before.sec + after.sec;
        time.min = before.min + after.min;
        time.hour = before.hour + after.hour;

        time.min += time.sec / 60;
        time.sec = time.sec % 60;
        time.hour += time.min / 60;
        time.min = time.min % 60;
        time.hour = time.hour % 24;

        return time;
    }

    public static Time subtraction(Time before, Time after){
        Time time = new Time();
        time.sec = before.sec - after.sec;
        time.min = before.min - after.min;
        time.hour = before.hour - after.hour;

        if (time.sec < 0) {
            time.sec = 60 + time.sec;
            time.min--;
        }

        if (time.min < 0) {
            time.min = 60 + time.min;
            time.hour--;
        }

        if (time.hour < 0) {
            time.hour = 24 + time.hour;
        }
        return time;
    }

    public static boolean isOverTime(Time before, Time after){
        Time time = new Time();
        time.sec = before.sec - after.sec;
        time.min = before.min - after.min;
        time.hour = before.hour - after.hour;

        if (time.sec < 0) {
            time.sec = 60 + time.sec;
            time.min--;
        }

        if (time.min < 0) {
            time.min = 60 + time.min;
            time.hour--;
        }

        return time.hour >= 0;
    }

    public String getTimeString(){
        String hourString = hour > 9 ? "" + hour : "0" + hour;
        String minString = min > 9 ? ":" + min : ":0" + min;
        String secString = sec > 9 ? ":" + sec : ":0" + sec;
        return hourString + minString + secString;
    }
}
