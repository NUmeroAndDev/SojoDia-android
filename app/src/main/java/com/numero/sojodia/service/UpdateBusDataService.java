package com.numero.sojodia.service;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import com.numero.sojodia.R;
import com.numero.sojodia.api.ApiClient;
import com.numero.sojodia.manager.DataManager;
import com.numero.sojodia.manager.UpdateManager;
import com.numero.sojodia.model.BusDataFile;
import com.numero.sojodia.util.BroadCastUtil;
import com.numero.sojodia.util.NetworkUtil;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.ResponseBody;

public class UpdateBusDataService extends IntentService {

    public final static int UPDATE_NOTIFICATION_ID = 1;

    private BusDataFile[] busDataFiles = BusDataFile.values();
    private ApiClient apiClient;
    private UpdateManager updateManager;
    private DataManager dataManager;

    public UpdateBusDataService() {
        super(UpdateBusDataService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        apiClient = new ApiClient();
        updateManager = UpdateManager.getInstance(this);
        dataManager = DataManager.getInstance(this);
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
        if (!NetworkUtil.canNetworkConnect(this) ||
                updateManager.isTodayUpdateChecked()) {
            stopSelf();
            return;
        }

        checkUpdate();
    }

    private void checkUpdate() {
        Request request = new Request.Builder().url("https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/version.txt").build();
        apiClient.execute(request, new ApiClient.Callback() {
            @Override
            public void onSuccess(ResponseBody responseBody) throws IOException {
                String data = responseBody.string();
                updateManager.setVersionCode(Long.valueOf(data));

                if (updateManager.canUpdate()) {
                    executeUpdate();
                }
            }

            @Override
            public void onFailed(Throwable e) {
                e.printStackTrace();
                stopSelf();
            }
        });
    }

    private void executeUpdate() {
        showNotification();
        for (final BusDataFile busDataFile : busDataFiles) {
            final Request request = new Request.Builder().url(busDataFile.getUrl()).build();
            apiClient.execute(request, new ApiClient.Callback() {
                @Override
                public void onSuccess(ResponseBody responseBody) throws IOException {
                    dataManager.saveDownLoadedData(busDataFile.getFileName(), responseBody.byteStream());
                }

                @Override
                public void onFailed(Throwable e) {
                    e.printStackTrace();
                    stopSelf();
                }
            });
        }
        updateManager.updateVersionCode();
        BroadCastUtil.sendBroadCast(this, BroadCastUtil.ACTION_FINISH_DOWNLOAD);
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
