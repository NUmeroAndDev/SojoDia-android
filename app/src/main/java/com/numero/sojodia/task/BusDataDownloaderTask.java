package com.numero.sojodia.task;

import android.content.Context;
import android.os.AsyncTask;

import com.numero.sojodia.model.BusDataFile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BusDataDownloaderTask extends AsyncTask<Void, Void, Void> {

    public final static int RESULT_OK = 0;
    public final static int RESULT_ERROR = 1;

    private Context context;
    private OkHttpClient okHttpClient;
    private Callback callback;
    private List<BusDataFile> fileList;
    private int resultCode;

    BusDataDownloaderTask(Context context) {
        this.context = context;
        okHttpClient = new OkHttpClient();
    }

    public static BusDataDownloaderTask init(Context context) {
        return new BusDataDownloaderTask(context);
    }

    public BusDataDownloaderTask setBusDataFileList(List<BusDataFile> fileList) {
        this.fileList = fileList;
        return this;
    }

    public BusDataDownloaderTask execute(Callback callback) {
        this.callback = callback;
        super.execute();
        return this;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (fileList != null) {
            for (BusDataFile file : fileList) {
                connectExecute(file);
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... progress) {
        if (callback != null) {
            callback.onLoading();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback == null) {
            return;
        }
        if (resultCode == RESULT_OK) {
            callback.onSuccess();
        } else {
            callback.onFailure();
        }
    }

    private void connectExecute(final BusDataFile file) {
        Request request = new Request.Builder().url(file.url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();

            InputStream inputStream = response.body().byteStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            FileOutputStream fileOutputStream = context.openFileOutput(file.name, Context.MODE_PRIVATE);
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

            resultCode = BusDataDownloaderTask.RESULT_OK;
        } catch (IOException e) {
            e.printStackTrace();
            resultCode = BusDataDownloaderTask.RESULT_ERROR;
        }
    }

    public interface Callback {
        void onSuccess();

        void onFailure();

        void onLoading();
    }
}
