package com.numero.sojodia.util;

import com.numero.sojodia.model.Time;

public class TimeUtil {

    public static Time addition(Time before, Time after) {
        int hour = before.getHour() + after.getHour();
        int min = before.getMin() + after.getMin();
        int sec = before.getSec() + after.getSec();

        min += sec / 60;
        sec = sec % 60;
        hour += min / 60;
        min = min % 60;
        hour = hour % 24;

        return new Time(hour, min, sec);
    }

    public static Time subtraction(Time before, Time after) {
        int hour = before.getHour() - after.getHour();
        int min = before.getMin() - after.getMin();
        int sec = before.getSec() - after.getSec();

        if (sec < 0) {
            sec = 60 + sec;
            min -= 1;
        }

        if (min < 0) {
            min = 60 + min;
            hour -= 1;
        }

        if (hour < 0) {
            hour = 24 + hour;
        }
        return new Time(hour, min, sec);
    }

    public static Time getCountTime(Time before) {
        Time time = new Time();
        int hour = time.getHour();
        int min = time.getMin();
        int sec= time.getSec();

        if (hour == 0 && min == 0 && sec == 0) {
            sec = 1;
        }
        sec = before.getSec() - sec;
        min = before.getMin() - min;
        hour = before.getHour() - hour;

        if (sec < 0) {
            sec = 60 + sec;
            min -= 1;
        }

        if (min < 0) {
            min = 60 + min;
            hour -= 1;
        }

        if (hour < 0) {
            hour = 24 + hour;
        }
        return new Time(hour, min, sec);
    }

    public static boolean isOverTime(Time before, Time after) {
        int sec = after.getSec() - before.getSec();
        int min = after.getMin() - before.getMin();
        int hour = after.getHour() - before.getHour();

        if (sec < 0) {
            sec = 60 + sec;
            min--;
        }

        if (min < 0) {
            min = 60 + min;
            hour--;
        }

        if (hour == 0 && min == 0 && sec == 0) {
            return false;
        }
        return hour >= 0;
    }

}
