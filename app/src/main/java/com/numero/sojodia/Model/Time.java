package com.numero.sojodia.Model;

public class Time {
    public int hour, min, sec;

    public Time() {
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

//    ToDo: delete method
    public String getTime() {
        return (hour > 9 ? "" + hour : "0" + hour) + (min > 9 ? ":" + min : ":0" + min) + (sec > 9 ? ":" + sec : ":0" + sec);
    }

    public void add(Time after) {
        sec += after.sec;
        min += after.min;
        hour += after.hour;

        min += sec / 60;
        sec = sec % 60;
        hour += min / 60;
        min = min % 60;
        hour = hour % 24;
    }

    public static Time subtraction(Time before, Time after){
        Time time = new Time();
        time.sec = after.sec - before.sec;
        time.min = after.min - before.min;
        time.hour = after.hour - before.hour;

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

    public static String getTimeString(Time time){
        String hourString = time.hour > 9 ? "" + time.hour : "0" + time.hour;
        String minString = time.min > 9 ? ":" + time.min : ":0" + time.min;
        String secString = time.sec > 9 ? ":" + time.sec : ":0" + time.sec;
        return hourString + minString + secString;
    }

    public static boolean isMinusTime(Time time) {
        return time.hour < 0;
    }
}
