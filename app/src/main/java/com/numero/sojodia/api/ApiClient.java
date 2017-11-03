package com.numero.sojodia.api;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiClient {

    private OkHttpClient okHttpClient;

    public ApiClient() {
        okHttpClient = new OkHttpClient();
    }

    // Serviceから呼ぶためUIスレッドにしない
    public void execute(@NonNull Request request, @NonNull SuccessCallback successCallback, @NonNull ErrorCallback errorCallback) {
        Observable.create((ObservableOnSubscribe<String>) e -> {
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                e.onNext(responseBody.string());
            } else {
                e.onError(new Exception("Response data is null"));
            }
            e.onComplete();
        }).subscribe(successCallback::onSuccess, errorCallback::onError);
    }

    public interface SuccessCallback {
        void onSuccess(String data) throws Exception;
    }

    public interface ErrorCallback {
        void onError(Throwable e);
    }
}
