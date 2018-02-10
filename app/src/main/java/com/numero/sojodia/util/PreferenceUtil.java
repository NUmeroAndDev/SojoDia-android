package com.numero.sojodia.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

    public static final String PREFERENCES = "PREFERENCES";
    public static final String VERSION_CODE = "FIRST_BOOT";
    public static final String DATE = "DATE";

    public static long getVersionCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(VERSION_CODE, 20170401L);
    }
}
