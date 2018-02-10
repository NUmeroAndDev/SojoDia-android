package com.numero.sojodia.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.numero.sojodia.SojoDiaApplication;
import com.numero.sojodia.api.BusDataApi;
import com.numero.sojodia.di.ApplicationComponent;
import com.numero.sojodia.manager.NotificationManager;
import com.numero.sojodia.model.BusDataFile;
import com.numero.sojodia.repository.ConfigRepository;
import com.numero.sojodia.util.BroadCastUtil;
import com.numero.sojodia.util.NetworkUtil;

import java.io.FileOutputStream;

import javax.inject.Inject;

import io.reactivex.Observable;

public class UpdateBusDataService extends IntentService {

    private BusDataFile[] busDataFiles = BusDataFile.values();
    private NotificationManager notificationManager;

    @Inject
    BusDataApi busDataApi;
    @Inject
    ConfigRepository configRepository;

    public UpdateBusDataService() {
        super(UpdateBusDataService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getComponent().inject(this);

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
                configRepository.isTodayUpdateChecked()) {
            stopSelf();
            return;
        }

        checkUpdate();
    }

    private ApplicationComponent getComponent() {
        return ((SojoDiaApplication) getApplication()).getComponent();
    }

    private void checkUpdate() {
        busDataApi.getBusDataVersion()
                .subscribe(s -> {
                    configRepository.setVersionCode(Long.valueOf(s));

                    if (configRepository.canUpdate()) {
                        executeUpdate();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    stopSelf();
                });
    }

    private void executeUpdate() {
        notificationManager.showNotification();
        Observable.fromArray(busDataFiles)
                .flatMap(busDataFile -> busDataApi.getBusData(busDataFile)
                        .doOnNext(s -> saveDownLoadData(busDataFile.getFileName(), s)))
                .subscribe(s -> {
                }, throwable -> {
                    throwable.printStackTrace();
                    stopSelf();
                }, () -> {
                    configRepository.successUpdate();
                    BroadCastUtil.sendBroadCast(this, BroadCastUtil.ACTION_FINISH_DOWNLOAD);
                    stopSelf();
                });
    }

    private void saveDownLoadData(String fileName, @NonNull String data) throws Exception {
        FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
        fileOutputStream.write(data.getBytes());
        fileOutputStream.close();
    }
}
