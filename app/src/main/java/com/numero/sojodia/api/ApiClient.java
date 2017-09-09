package com.numero.sojodia.api;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiClient {

    private OkHttpClient okHttpClient;

    public ApiClient() {
        okHttpClient = new OkHttpClient();
    }

    public void execute(@NonNull Request request, @NonNull Callback callback) {
        try {
            Response response = okHttpClient.newCall(request).execute();
            callback.onSuccess(response.body());
        } catch (IOException e) {
            callback.onFailed(e);
        }
    }

    // このコールバックはThreadで返される
    public interface Callback {
        void onSuccess(ResponseBody responseBody) throws IOException;

        void onFailed(Throwable e);
    }
}
