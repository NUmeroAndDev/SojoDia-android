package com.numero.sojodia.manager;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;

import com.numero.sojodia.util.DateUtil;
import com.numero.sojodia.util.PreferenceUtil;

public class UpdateManager extends ContextWrapper {

    private static UpdateManager INSTANCE;
    private long versionCode = -1;

    public static UpdateManager getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UpdateManager(context);
        }
        return INSTANCE;
    }

    public UpdateManager(Context context) {
        super(context);
    }

    public void setVersionCode(long versionCode) {
        this.versionCode = versionCode;
        // バージョンがセットされた時はチェックに成功した場合
        PreferenceUtil.setUpdateCheckDate(getBaseContext(), DateUtil.getTodayStringOnlyFigure());
    }

    public void updateVersionCode() {
        PreferenceUtil.setVersionCode(getBaseContext(), versionCode);
    }

    // アップデートの確認は1日1回
    public boolean isTodayUpdateChecked() {
        String previousCheckDateString = PreferenceUtil.getPreviousUpdateCheckDate(getBaseContext());
        return DateUtil.getTodayStringOnlyFigure().equals(previousCheckDateString);
    }

    public boolean canUpdate() {
        return PreferenceUtil.getVersionCode(getBaseContext()) < versionCode;
    }
}
