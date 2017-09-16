package com.numero.sojodia.util;

import com.numero.sojodia.model.Time;

public class TimeUtil {

    public static Time addition(Time before, Time after) {
        Time time = new Time(
                before.getHour() + after.getHour(),
                before.getMin() + after.getMin(),
                before.getSec() + after.getSec()
        );

        time.setMin(time.getMin() + time.getSec() / 60);
        time.setSec(time.getSec() % 60);
        time.setHour(time.getHour() + time.getMin() / 60);
        time.setMin(time.getMin() % 60);
        time.setHour(time.getHour() % 24);

        return time;
    }

    public static Time subtraction(Time before, Time after) {
        Time time = new Time(
                before.getHour() - after.getHour(),
                before.getMin() - after.getMin(),
                before.getSec() - after.getSec()
        );

        if (time.getSec() < 0) {
            time.setSec(60 + time.getSec());
            time.setMin(time.getMin() - 1);
        }

        if (time.getMin() < 0) {
            time.setMin(60 + time.getMin());
            time.setHour(time.getHour() - 1);
        }

        if (time.getHour() < 0) {
            time.setHour(24 + time.getHour());
        }
        return time;
    }

    public static Time getCountTime(Time before) {
        Time time = new Time();
        if (time.getHour() == 0 && time.getMin() == 0 && time.getSec() == 0) {
            time.setSec(1);
        }
        time.setSec(before.getSec() - time.getSec());
        time.setMin(before.getMin() - time.getMin());
        time.setHour(before.getHour() - time.getHour());

        if (time.getSec() < 0) {
            time.setSec(60 + time.getSec());
            time.setMin(time.getMin() - 1);
        }

        if (time.getMin() < 0) {
            time.setMin(60 + time.getMin());
            time.setHour(time.getHour() - 1);
        }

        if (time.getHour() < 0) {
            time.setHour(24 + time.getHour());
        }
        return time;
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
