package com.numero.sojodia.util;

import android.content.Context;
import android.text.format.DateFormat;

import com.numero.sojodia.R;

import java.util.Calendar;

public class DateUtil {

    public static final int WEEKDAY = 0;
    public static final int SATURDAY = 1;
    public static final int SUNDAY = 2;

    public static String getTodayStringOnlyFigure(){
        return DateFormat.format("yyyyMMdd", Calendar.getInstance()).toString();
    }

    public static String getTodayString(Context context) {
        return DateFormat.format(context.getString(R.string.date_pattern), Calendar.getInstance()).toString();
    }
}
