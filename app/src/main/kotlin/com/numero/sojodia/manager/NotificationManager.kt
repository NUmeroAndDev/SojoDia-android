package com.numero.sojodia.manager

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat

import com.numero.sojodia.R

class NotificationManager(context: Context) : ContextWrapper(context) {

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initNotificationChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, getString(R.string.notification_channel_name), android.app.NotificationManager.IMPORTANCE_LOW).apply {
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        manager.createNotificationChannel(channel)
    }

    fun showNotification() {
        NotificationManagerCompat.from(baseContext).notify(UPDATE_NOTIFICATION_ID, buildNotification().apply {
            flags = Notification.FLAG_ONGOING_EVENT
        })
    }

    fun cancelNotification() {
        NotificationManagerCompat.from(baseContext).cancel(UPDATE_NOTIFICATION_ID)
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(applicationContext, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_notification)
            setContentTitle(getString(R.string.notification_update_title))
            setContentText(getString(R.string.notification_update_text))
            setProgress(0, 0, true)
        }.build()
    }

    companion object {

        const val UPDATE_NOTIFICATION_ID = 1

        private const val CHANNEL_ID = "BUS_DIA_CHANNEL_ID"
    }
}
