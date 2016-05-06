package com.numero.sojodia;

public class Time {
    int hour, min, sec;

    Time() {
    }

    Time(int hour, int min, int sec) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }

    public void setTime(int hour, int min, int sec) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }

    public String getTime() {
        return (hour > 9 ? "" + hour : "0" + hour) + (min > 9 ? ":" + min : ":0" + min) + (sec > 9 ? ":" + sec : ":0" + sec);
    }

    public void plus(Time after) {
        sec += after.sec;
        min += after.min;
        hour += after.hour;

        min += sec / 60;
        sec = sec % 60;
        hour += min / 60;
        min = min % 60;
        hour = hour % 24;
    }

    public void minus(Time after) {
        sec = after.sec - sec;
        min = after.min - min;
        hour = after.hour - hour;

        if (sec < 0) {
            sec = 60 + sec;
            min--;
        }

        if (min < 0) {
            min = 60 + min;
            hour--;
        }

        if (hour < 0) {
            hour = 24 + hour;
        }
    }

    public boolean isMinus(Time after) {
        sec = after.sec - sec;
        min = after.min - min;
        hour = after.hour - hour;

        if (sec < 0) {
            sec = 60 + sec;
            min--;
        }

        if (min < 0) {
            min = 60 + min;
            hour--;
        }

        return hour < 0;
    }
}
