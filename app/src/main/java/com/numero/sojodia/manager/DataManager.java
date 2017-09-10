package com.numero.sojodia.manager;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataManager extends ContextWrapper {

    private static DataManager INSTANCE;

    public static DataManager getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataManager(context);
        }
        return INSTANCE;
    }

    private DataManager(@NonNull Context context) {
        super(context);
    }

    public String getBusTimeDataSource(String fileName) {
        try {
            BufferedReader bufferedReader;
            FileInputStream fileInputStream = null;
            InputStream inputStream = null;

            // ダウンロードしたファイルがなければアセットから取得
            File file = new File(getFilesDir() + "/" + fileName);
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
            } else {
                AssetManager assetManager = getResources().getAssets();
                inputStream = assetManager.open(fileName);
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            }

            StringBuilder builder = new StringBuilder();
            String lineString;
            boolean isFirstLine = true;
            while ((lineString = bufferedReader.readLine()) != null) {
                if (isFirstLine) {
                    // 最初の行はデータではないためスキップ
                    isFirstLine = false;
                    continue;
                }
                builder.append(lineString);
                builder.append("\n");
            }

            bufferedReader.close();
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return builder.toString();
        } catch (IOException e) {
            return null;
        }
    }

    public void saveDownLoadedData(String fileName, @NonNull InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);

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
    }
}
