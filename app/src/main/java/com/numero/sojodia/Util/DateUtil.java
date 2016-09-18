package com.numero.sojodia.Util;

import android.content.Context;
import android.text.format.DateFormat;

import com.numero.sojodia.R;

import java.util.Calendar;

public class DateUtil {

    public static String getTodayStringOnlyFigure(){
        return DateFormat.format("yyyyMMdd", Calendar.getInstance()).toString();
    }

    public static String getTodayString(Context context) {
        return DateFormat.format(context.getString(R.string.date_pattern), Calendar.getInstance()).toString();
    }
}
