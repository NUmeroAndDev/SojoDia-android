package com.numero.sojodia;

import java.util.Calendar;

public class Holiday {
    private Calendar calendar;

    Holiday(){
        calendar = Calendar.getInstance();
    }

    public void setToday(){
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }

    public boolean isHoliday(){
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK);

        boolean flg = false;

        switch (month) {
            case Calendar.JANUARY:
                flg = (day == 1 || day == 2 && week == Calendar.MONDAY || weekInMonth(day, Calendar.MONDAY, 2));
                break;
            case Calendar.FEBRUARY:
                flg = (day == 11 || day == 12 && week == Calendar.MONDAY);
                break;
            case Calendar.MARCH:
                switch (calendar.get(Calendar.YEAR)){
                    case 2016:
                    case 2017:
                        flg = (day == 20 || (day == 21 && week == Calendar.MONDAY));
                        break;
                    case 2018:
                    case 2019:
                        flg = (day == 21 || (day == 22 && week == Calendar.MONDAY));
                        break;
                }
                break;
            case Calendar.APRIL:
                flg = (day == 29 || (day == 30 && week == Calendar.MONDAY));
                break;
            case Calendar.MAY:
                if(day == 3 || (day == 7 && week == Calendar.WEDNESDAY)){
                    flg = true;
                }else if(day == 4 || (day == 6 && week == Calendar.TUESDAY)){
                    flg = true;
                }else if(day == 5 || (day == 6 && week == Calendar.MONDAY)){
                    flg = true;
                }
                break;
            case Calendar.JUNE:
                break;
            case Calendar.JULY:
                flg = weekInMonth(day, Calendar.MONDAY, 3);
                break;
            case Calendar.AUGUST:
                flg = (day == 11 || day == 12 && week == Calendar.MONDAY);
                break;
            case Calendar.SEPTEMBER:
                switch (calendar.get(Calendar.YEAR)) {
                    case 2016:
                        flg = (day == 22 || day == 23 && week == Calendar.MONDAY || weekInMonth(day, Calendar.MONDAY, 3));
                        break;
                    case 2017:
                    case 2018:
                    case 2019:
                        flg = (day == 23 || day == 24 && week == Calendar.MONDAY || weekInMonth(day, Calendar.MONDAY, 3));
                        break;
                }
                break;
            case Calendar.OCTOBER:
                flg = weekInMonth(day, Calendar.MONDAY, 2);
                break;
            case Calendar.NOVEMBER:
                if(day == 3 || day == 4 && week == Calendar.MONDAY){
                    flg = true;
                }else if(day == 23 || day == 24 && week == Calendar.MONDAY){
                    flg = true;
                }
                break;
            case Calendar.DECEMBER:
                flg = (day == 23 || day == 24 && week == Calendar.MONDAY);
                break;
        }
        return flg;
    }

    public boolean isSchool(){
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK);

        boolean flg = false;
        switch (month) {
            case Calendar.JANUARY:
                break;
            case Calendar.FEBRUARY:
                break;
            case Calendar.MARCH:
                break;
            case Calendar.APRIL:
                flg = (day == 29 || day == 30 && week == Calendar.MONDAY);
                break;
            case Calendar.MAY:
                break;
            case Calendar.JUNE:
                break;
            case Calendar.JULY:
                flg = weekInMonth(day, Calendar.MONDAY, 3);
                break;
            case Calendar.AUGUST:
                break;
            case Calendar.SEPTEMBER:
                break;
            case Calendar.OCTOBER:
                flg = weekInMonth(day, Calendar.MONDAY, 2);
                break;
            case Calendar.NOVEMBER:
                flg = (day == 3 || day == 4 && week == Calendar.MONDAY);
                break;
            case Calendar.DECEMBER:
                break;
        }
        return flg;
    }

    private boolean weekInMonth(int toDay, int week, int count){
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.calendar.get(Calendar.YEAR),this.calendar.get(Calendar.MONTH),this.calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, count);
        calendar.set(Calendar.DAY_OF_WEEK, week);

        return toDay == calendar.get(Calendar.DAY_OF_MONTH);
    }
}
