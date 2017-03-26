package com.numero.sojodia.network;

import android.content.Context;
import android.os.AsyncTask;

import com.numero.sojodia.model.BusDataFile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class BusDataDownloader extends AsyncTask<Void, Void, Void> {

    public final static int RESULT_OK = 0;
    public final static int RESULT_ERROR = 1;

    private Context context;
    private Callback callback;
    private List<BusDataFile> fileList;
    private int resultCode;

    BusDataDownloader(Context context){
        this.context = context;
    }

    public static BusDataDownloader init(Context context) {
        return new BusDataDownloader(context);
    }

    public BusDataDownloader setBusDataFileList(List<BusDataFile> fileList) {
        this.fileList = fileList;
        return this;
    }

    public BusDataDownloader setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (fileList != null) {
            for(BusDataFile file : fileList){
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

    private void connectExecute(BusDataFile file) {
        try {
            URL url = new URL(file.url);
            InputStream inputStream = url.openConnection().getInputStream();

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

            resultCode = BusDataDownloader.RESULT_OK;
        } catch (IOException e) {
            e.printStackTrace();
            resultCode = BusDataDownloader.RESULT_ERROR;
        }
    }

    public interface Callback {
        void onSuccess();

        void onFailure();

        void onLoading();
    }
}
