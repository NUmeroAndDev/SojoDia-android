package com.numero.sojodia.Network;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public abstract class UpdateChecker extends AsyncTask<Void, Void, Void>{

    public final static int RESULT_OK = 0;
    public final static int RESULT_ERROR = 1;

    private long versionCode = 0L;
    private int resultCode;

    public UpdateChecker(){
    }

    @Override
    protected Void doInBackground(Void... voids) {
        connectExecute("https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/version.txt");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        callbackOnPostExecute(resultCode, versionCode);
    }

    private void connectExecute(String urlString) {
        try {
            URL url = new URL(urlString);
            InputStream inputStream = url.openConnection().getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            StringBuilder stringBuilder = new StringBuilder();
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                stringBuilder.append(tmp);
            }

            versionCode = Long.valueOf(stringBuilder.toString());
            reader.close();
            inputStream.close();

            resultCode = BusDataDownloader.RESULT_OK;
        } catch (IOException e) {
            e.printStackTrace();
            resultCode = BusDataDownloader.RESULT_ERROR;
        }
    }

    public abstract void callbackOnPostExecute(int resultCode, long versionCode);
}
