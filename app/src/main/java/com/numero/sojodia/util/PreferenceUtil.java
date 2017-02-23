package com.numero.sojodia.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

    public static long getVersionCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(Constants.VERSION_CODE, 20160401L);
    }

    public static void setVersionCode(Context context, long code) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(Constants.VERSION_CODE, code);
        edit.apply();
    }

    public static String getPreviousUpdatedDate(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.DATE, "");
    }

    public static void setUpdatedDate(Context context, String date) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constants.DATE, date);
        edit.apply();
    }
}
