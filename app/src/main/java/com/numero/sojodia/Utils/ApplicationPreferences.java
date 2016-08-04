package com.numero.sojodia.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationPreferences {

    public static boolean isFirstBoot(Context context){
        SharedPreferences pref = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        return pref.getBoolean(Constants.FIRST_BOOT, true);
    }

    public static void setFirstBoot(Context context, boolean isFirstBoot){
        SharedPreferences pref = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean(Constants.FIRST_BOOT, isFirstBoot);
        edit.apply();
    }
}
