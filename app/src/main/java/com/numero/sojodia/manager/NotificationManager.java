package com.numero.sojodia.manager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.numero.sojodia.R;

public class NotificationManager extends ContextWrapper {

    public final static int UPDATE_NOTIFICATION_ID = 1;

    private static final String CHANNEL_ID = "BUS_DIA_CHANNEL_ID";

    public NotificationManager(Context context) {
        super(context);

        initNotificationChannel();
    }

    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, getString(R.string.notification_channel_name), android.app.NotificationManager.IMPORTANCE_LOW);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            android.app.NotificationManager manager = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    public void showNotification() {
        Notification notification = buildNotification();
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        NotificationManagerCompat manager = NotificationManagerCompat.from(getBaseContext());
        manager.notify(UPDATE_NOTIFICATION_ID, notification);
    }

    public void cancelNotification() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(getBaseContext());
        manager.cancel(UPDATE_NOTIFICATION_ID);
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
