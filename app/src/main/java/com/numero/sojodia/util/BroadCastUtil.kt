package com.numero.sojodia.util

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager

object BroadCastUtil {

    const val ACTION_FINISH_DOWNLOAD = "ACTION_FINISH_DOWNLOAD"

    fun sendBroadCast(context: Context?, action: String) {
        context ?: return
        LocalBroadcastManager.getInstance(context).sendBroadcast(Intent().also {
            it.action = action
        })
    }

}
