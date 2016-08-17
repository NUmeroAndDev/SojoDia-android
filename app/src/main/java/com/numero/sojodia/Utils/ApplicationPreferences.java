package com.numero.sojodia.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationPreferences {

    public static int getPreviousVersionCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Constants.VERSION_CODE, 0);
    }

    public static void setVersionCode(Context context, int code) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(Constants.VERSION_CODE, code);
        edit.apply();
    }
}
