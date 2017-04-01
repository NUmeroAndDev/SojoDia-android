package com.numero.sojodia.task;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateCheckTask extends AsyncTask<Void, Void, Void> {

    public final static int RESULT_OK = 0;
    public final static int RESULT_ERROR = 1;

    private Callback callback;
    private OkHttpClient okHttpClient;
    private long versionCode = 0L;
    private int resultCode;

    UpdateCheckTask() {
        okHttpClient = new OkHttpClient();
    }

    public static UpdateCheckTask init() {
        return new UpdateCheckTask();
    }

    public UpdateCheckTask execute(Callback callback) {
        this.callback = callback;
        super.execute();
        return this;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        executeConnect("https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/version.txt");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback == null) {
            return;
        }
        if (resultCode == RESULT_OK) {
            callback.onSuccess(versionCode);
        } else {
            callback.onFailure();
        }
    }

    private void executeConnect(String urlString) {
        Request request = new Request.Builder().url(urlString).build();
        try {
            Response response = okHttpClient.newCall(request).execute();

            String responseString = response.body().string();
            versionCode = Long.valueOf(responseString);

            response.close();
            resultCode = RESULT_OK;
        } catch (IOException e) {
            e.printStackTrace();
            resultCode = RESULT_ERROR;
        }
    }

    public interface Callback {
        void onSuccess(long versionCode);

        void onFailure();
    }
}
