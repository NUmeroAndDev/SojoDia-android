package com.numero.sojodia.service;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import com.numero.sojodia.R;
import com.numero.sojodia.helper.DownloadHelper;
import com.numero.sojodia.helper.UpdateCheckHelper;
import com.numero.sojodia.model.BusDataFile;
import com.numero.sojodia.util.BroadCastUtil;
import com.numero.sojodia.util.DateUtil;
import com.numero.sojodia.util.NetworkUtil;
import com.numero.sojodia.util.PreferenceUtil;

import java.io.IOException;

import okhttp3.OkHttpClient;

public class UpdateBusDataService extends IntentService {

    public final static int UPDATE_NOTIFICATION_ID = 1;

    private BusDataFile[] busDataFiles = BusDataFile.values();
    private OkHttpClient okHttpClient;

    public UpdateBusDataService() {
        super(UpdateBusDataService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        okHttpClient = new OkHttpClient();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.cancel(UPDATE_NOTIFICATION_ID);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!NetworkUtil.canNetworkConnect(this)) {
            stopSelf();
            return;
        }
        if (UpdateCheckHelper.isTodayUpdateChecked(this)) {
            stopSelf();
            return;
        }

        long versionCode = checkVersionCode();
        if (checkVersionCode() == -1) {
            stopSelf();
            return;
        }
        PreferenceUtil.setUpdateCheckDate(this, DateUtil.getTodayStringOnlyFigure());
        if (UpdateCheckHelper.canUpdate(this, versionCode)) {
            showNotification();
            if (downloadBusDataFile()) {
                PreferenceUtil.setVersionCode(this, versionCode);
                BroadCastUtil.sendBroadCast(this, BroadCastUtil.ACTION_FINISH_DOWNLOAD);
            }
        }
    }

    private long checkVersionCode() {
        try {
            return UpdateCheckHelper.executeCheck(okHttpClient);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private boolean downloadBusDataFile() {
        try {
            for (BusDataFile dataFile : busDataFiles) {
                DownloadHelper.executeDownload(this, okHttpClient, dataFile.getUrl(), dataFile.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
            return false;
        }
        return true;
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_notification);

        builder.setContentTitle(getString(R.string.notification_update_title));
        builder.setContentText(getString(R.string.notification_update_text));
        builder.setProgress(0, 0, true);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(UPDATE_NOTIFICATION_ID, notification);
    }
}
