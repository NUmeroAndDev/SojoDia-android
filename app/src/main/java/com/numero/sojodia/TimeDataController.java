package com.numero.sojodia;

import com.numero.sojodia.Model.TimeData;
import com.numero.sojodia.Model.TimeTableItem;
import com.numero.sojodia.Utils.Constants;

import java.util.ArrayList;

public class TimeDataController {

    public static final int WEEKDAY = Constants.WEEKDAY;
    public static final int SATURDAY = Constants.SATURDAY;
    public static final int SUNDAY = Constants.SUNDAY;
    public static final int HOLIDAY_IN_SCHOOL = Constants.HOLIDAY_IN_SCHOOL;
    public static final int ALL = Constants.TIME_TABLE_ALL;

    public final static int RECIPROCATE_GOING = 0;
    public final static int RECIPROCATE_RETURN = 1;

    public static void setTKTimeList(ArrayList<TimeTableItem> items, int reciprocating, int week) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.clear();

        initTKTimeList(items, reciprocating, week);
    }

    public static void setTNDTimeList(ArrayList<TimeTableItem> items, int reciprocating, int week) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.clear();

        initTNDTimeList(items, reciprocating, week);
    }

    private static void initTKTimeList(ArrayList<TimeTableItem> items, int reciprocating, int week) {
        switch (reciprocating) {
            case RECIPROCATE_GOING:
                setGoingTKTimeList(items, week);
                break;
            case RECIPROCATE_RETURN:
                setReturnTKTimeList(items, week);
                break;
        }
    }

    private static void initTNDTimeList(ArrayList<TimeTableItem> items, int reciprocating, int week) {
        switch (reciprocating) {
            case RECIPROCATE_GOING:
                setGoingTNDTimeList(items, week);
                break;
            case RECIPROCATE_RETURN:
                setReturnTNDTimeList(items, week);
                break;
        }
    }

    //高槻→関大
    private static void setGoingTKTimeList(ArrayList<TimeTableItem> items, int week){
        switch (week) {
            case WEEKDAY:
                TimeData.setWeekdayGoingTKTime(items);
                break;
            case SATURDAY:
                TimeData.setSaturdayGoingTKTime(items);
                break;
            case SUNDAY:
                TimeData.setSundayGoingTKTime(items);
                break;
            case HOLIDAY_IN_SCHOOL:
                TimeData.setHolidayInSchoolGoingTKTime(items);
                break;
            case ALL:
                TimeData.setWeekdayGoingTKTime(items);
                TimeData.setSaturdayGoingTKTime(items);
                TimeData.setSundayGoingTKTime(items);
                TimeData.setHolidayInSchoolGoingTKTime(items);
                break;
        }
    }

    //関大→高槻
    private static void setReturnTKTimeList(ArrayList<TimeTableItem> items, int week){
        switch (week) {
            case WEEKDAY:
                TimeData.setWeekdayReturnTKTime(items);
                break;
            case SATURDAY:
                TimeData.setSaturdayReturnTKTime(items);
                break;
            case SUNDAY:
                TimeData.setSundayReturnTKTime(items);
                break;
            case HOLIDAY_IN_SCHOOL:
                TimeData.setHolidayInSchoolReturnTKTime(items);
                break;
            case ALL:
                TimeData.setWeekdayReturnTKTime(items);
                TimeData.setSaturdayReturnTKTime(items);
                TimeData.setSundayReturnTKTime(items);
                TimeData.setHolidayInSchoolReturnTKTime(items);
                break;
        }
    }

    //富田→関大
    private static void setGoingTNDTimeList(ArrayList<TimeTableItem> items, int week){
        switch (week) {
            case WEEKDAY:
                TimeData.setWeekdayGoingTNDTime(items);
                break;
            case SATURDAY:
                TimeData.setSaturdayGoingTNDTime(items);
                break;
            case SUNDAY:
                TimeData.setSundayGoingTNDTime(items);
                break;
            case HOLIDAY_IN_SCHOOL:
                TimeData.setHolidayInSchoolGoingTNDTime(items);
                break;
            case ALL:
                TimeData.setWeekdayGoingTNDTime(items);
                TimeData.setSaturdayGoingTNDTime(items);
                TimeData.setSundayGoingTNDTime(items);
                TimeData.setHolidayInSchoolGoingTNDTime(items);
                break;
        }
    }

    //関大→富田
    private static void setReturnTNDTimeList(ArrayList<TimeTableItem> items, int week){
        switch (week) {
            case WEEKDAY:
                TimeData.setWeekdayReturnTNDTime(items);
                break;
            case SATURDAY:
                TimeData.setSaturdayReturnTNDTime(items);
                break;
            case SUNDAY:
                TimeData.setSundayReturnTNDTime(items);
                break;
            case HOLIDAY_IN_SCHOOL:
                TimeData.setHolidayInSchoolReturnTNDTime(items);
                break;
            case ALL:
                TimeData.setWeekdayReturnTNDTime(items);
                TimeData.setSaturdayReturnTNDTime(items);
                TimeData.setSundayReturnTNDTime(items);
                TimeData.setHolidayInSchoolReturnTNDTime(items);
                break;
        }
    }

}

