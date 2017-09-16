package com.numero.sojodia.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class BroadCastUtil {

    public final static String ACTION_FINISH_DOWNLOAD = "ACTION_FINISH_DOWNLOAD";

    public final static String ACTION_CHANGED_DATE = "ACTION_CHANGED_DATE";

    public static void sendBroadCast(Context context, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}
