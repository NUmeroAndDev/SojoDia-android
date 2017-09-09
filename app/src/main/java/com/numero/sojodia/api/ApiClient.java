package com.numero.sojodia.api;

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

    public ResponseBody execute(Request request) throws IOException {
        Response response = okHttpClient.newCall(request).execute();
        return response.body();
    }
}
