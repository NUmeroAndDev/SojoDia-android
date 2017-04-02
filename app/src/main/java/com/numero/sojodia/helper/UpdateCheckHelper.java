package com.numero.sojodia.helper;

import android.content.Context;
import android.support.annotation.WorkerThread;

import com.numero.sojodia.util.DateUtil;
import com.numero.sojodia.util.PreferenceUtil;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateCheckHelper {

    @WorkerThread
    public static long executeCheck(OkHttpClient okHttpClient) throws IOException {
        String url = "https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/version.txt";

        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();

        String responseString = response.body().string();

        response.close();
        return Long.valueOf(responseString);
    }

    public static boolean isTodayUpdateChecked(Context context) {
        if (PreferenceUtil.getPreviousUpdateCheckDate(context).isEmpty()) {
            return false;
        } else if (!PreferenceUtil.getPreviousUpdateCheckDate(context).equals(DateUtil.getTodayStringOnlyFigure())) {
            return false;
        }
        return true;
    }

    public static boolean canUpdate(Context context, long versionCode) {
        return PreferenceUtil.getVersionCode(context) < versionCode;
    }
}
