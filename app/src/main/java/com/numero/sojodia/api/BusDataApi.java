package com.numero.sojodia.api;

import android.support.annotation.NonNull;

import com.numero.sojodia.model.BusDataFile;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BusDataApi {

    private static final String UPDATE_URL = "https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/version.txt";

    private final OkHttpClient okHttpClient;

    public BusDataApi(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public Observable<String> getBusData(BusDataFile busDataFile) {
        Request request = new Request.Builder().url(busDataFile.getUrl()).build();
        return createObservable(request);
    }

    public Observable<String> getBusDataVersion() {
        Request request = new Request.Builder().url(UPDATE_URL).build();
        return createObservable(request);
    }

    private Observable<String> createObservable(@NonNull Request request) {
        return Observable.create(e -> {
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new Exception(response.message());
            }
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                e.onNext(responseBody.string());
                e.onComplete();
            } else {
                e.onError(new Exception("Response data is null"));
            }
        });
    }
}
