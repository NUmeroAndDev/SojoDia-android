package com.numero.sojodia.Utils;

import java.util.Calendar;

public class DateUtil {

    public static String getDateString(){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        String yearString = String.valueOf(year);
        String monthString = month < 10 ? "0" + String.valueOf(month) : String.valueOf(month);
        String dayString = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);

        return yearString + monthString + dayString;
    }

}
