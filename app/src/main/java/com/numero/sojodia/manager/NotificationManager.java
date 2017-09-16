package com.numero.sojodia.manager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.numero.sojodia.R;

public class NotificationManager extends ContextWrapper {

    private static final String CHANNEL_ID = "BUS_DIA_CHANNEL_ID";

    public NotificationManager(Context context) {
        super(context);

        initNotificationChannel();
    }

    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            android.app.NotificationManager manager = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, getString(R.string.notification_channel_name), android.app.NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            manager.createNotificationChannel(channel);
        }
    }

    private Notification buildNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_notification);

        builder.setContentTitle(getString(R.string.notification_update_title));
        builder.setContentText(getString(R.string.notification_update_text));
        builder.setProgress(0, 0, true);

        return builder.build();
    }
}
