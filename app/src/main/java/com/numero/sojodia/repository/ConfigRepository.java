package com.numero.sojodia.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.numero.sojodia.util.DateUtil;

public class ConfigRepository implements IConfigRepository {

    private static final String PREFERENCES = "PREFERENCES";
    private static final String VERSION_CODE = "FIRST_BOOT";
    private static final String DATE = "DATE";

    private long versionCode = -1;

    private final SharedPreferences sharedPreferences;

    public ConfigRepository(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isTodayUpdateChecked() {
        String previousCheckDateString = sharedPreferences.getString(DATE, "");
        return DateUtil.getTodayStringOnlyFigure().equals(previousCheckDateString);
    }

    @Override
    public void successUpdate() {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(VERSION_CODE, versionCode);
        edit.apply();
    }

    @Override
    public boolean canUpdate() {
        return sharedPreferences.getLong(VERSION_CODE, 20170401L) < versionCode;
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
        // バージョンがセットされた時はチェックに成功した場合
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(DATE, DateUtil.getTodayStringOnlyFigure());
        edit.apply();
    }

    public long getVersionCode() {
        return sharedPreferences.getLong(VERSION_CODE, 20170401L);
    }
}
