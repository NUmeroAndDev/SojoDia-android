package com.numero.sojodia.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

    public static final String PREFERENCES = "PREFERENCES";
    public static final String VERSION_CODE = "FIRST_BOOT";
    public static final String DATE = "DATE";

    public static long getVersionCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(VERSION_CODE, 20160401L);
    }

    public static void setVersionCode(Context context, long code) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(VERSION_CODE, code);
        edit.apply();
    }

    public static String getPreviousUpdateCheckDate(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DATE, "");
    }

    public static void setUpdateCheckDate(Context context, String date) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(DATE, date);
        edit.apply();
    }
}
