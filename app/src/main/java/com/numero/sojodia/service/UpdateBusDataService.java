package com.numero.sojodia.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.numero.sojodia.api.ApiClient;
import com.numero.sojodia.manager.DataManager;
import com.numero.sojodia.manager.NotificationManager;
import com.numero.sojodia.manager.UpdateManager;
import com.numero.sojodia.model.BusDataFile;
import com.numero.sojodia.util.BroadCastUtil;
import com.numero.sojodia.util.NetworkUtil;

import okhttp3.Request;

public class UpdateBusDataService extends IntentService {

    private BusDataFile[] busDataFiles = BusDataFile.values();
    private ApiClient apiClient;
    private UpdateManager updateManager;
    private DataManager dataManager;
    private NotificationManager notificationManager;

    public UpdateBusDataService() {
        super(UpdateBusDataService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        apiClient = new ApiClient();
        updateManager = UpdateManager.getInstance(this);
        dataManager = DataManager.getInstance(this);
        notificationManager = new NotificationManager(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationManager.cancelNotification();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!NetworkUtil.canNetworkConnect(this) ||
                updateManager.isTodayUpdateChecked()) {
            stopSelf();
            return;
        }

        checkUpdate();
    }

    private void checkUpdate() {
        Request request = new Request.Builder().url("https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/version.txt").build();
        apiClient.execute(request, data -> {
            updateManager.setVersionCode(Long.valueOf(data));

            if (updateManager.canUpdate()) {
                executeUpdate();
            }
        }, e -> {
            e.printStackTrace();
            stopSelf();
        });
    }

    private void executeUpdate() {
        notificationManager.showNotification();
        for (final BusDataFile busDataFile : busDataFiles) {
            final Request request = new Request.Builder().url(busDataFile.getUrl()).build();
            apiClient.execute(request, data -> dataManager.saveDownLoadData(busDataFile.getFileName(), data), e -> {
                e.printStackTrace();
                stopSelf();
            });
        }
        updateManager.updateVersionCode();
        BroadCastUtil.sendBroadCast(this, BroadCastUtil.ACTION_FINISH_DOWNLOAD);
    }
}
