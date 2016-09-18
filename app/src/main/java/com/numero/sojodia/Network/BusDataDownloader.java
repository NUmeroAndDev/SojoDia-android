package com.numero.sojodia.Network;

import android.content.Context;
import android.os.AsyncTask;

import com.numero.sojodia.Model.BusDataFile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BusDataDownloader extends AsyncTask<BusDataFile, Void, Void> {

    public final static int RESULT_OK = 0;
    public final static int RESULT_ERROR = 1;

    private Context context;
    private Callback callback;
    private int resultCode;

    BusDataDownloader(Context context){
        this.context = context;
    }

    public static BusDataDownloader init(Context context) {
        return new BusDataDownloader(context);
    }

    public BusDataDownloader setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    protected Void doInBackground(BusDataFile... files) {
        for(BusDataFile file : files){
            connectExecute(file);
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
            URL url = new URL(file.URL);
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
