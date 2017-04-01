package com.numero.sojodia.helper;

import android.content.Context;
import android.support.annotation.WorkerThread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadHelper {

    @WorkerThread
    public static void executeDownload(Context context, OkHttpClient okHttpClient, String url, String fileName) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();

        InputStream inputStream = response.body().byteStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);

        byte[] buffer = new byte[4096];
        int readByte;

        while ((readByte = dataInputStream.read(buffer)) != -1) {
            dataOutputStream.write(buffer, 0, readByte);
        }

        dataOutputStream.close();
        fileOutputStream.close();
        dataInputStream.close();
        inputStream.close();
        response.body().close();
    }
}
