package com.numero.sojodia.repository;

import android.content.Context;
import android.content.res.AssetManager;

import com.numero.sojodia.model.BusDataFile;
import com.numero.sojodia.model.BusTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;

public class BusDataRepository implements IBusDataRepository {

    private Context context;

    public BusDataRepository(Context context) {
        this.context = context;
    }

    @Override
    public Single<List<BusTime>> loadBusData(BusDataFile busDataFile) {
        return Observable.create((ObservableOnSubscribe<String>) e -> {
            BufferedReader bufferedReader;
            FileInputStream fileInputStream = null;
            InputStream inputStream = null;

            // ダウンロードしたファイルがなければアセットから取得
            File file = new File(context.getFilesDir() + "/" + busDataFile.getFileName());
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
            } else {
                AssetManager assetManager = context.getResources().getAssets();
                inputStream = assetManager.open(busDataFile.getFileName());
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
            e.onNext(builder.toString());
        })
                .flatMap(s -> Observable.fromArray(s.split("\n")))
                .map(s -> new StringTokenizer(s, ","))
                .map(stringTokenizer -> {
                    int hour = Integer.valueOf(stringTokenizer.nextToken());
                    int minutes = Integer.valueOf(stringTokenizer.nextToken());
                    int week = Integer.valueOf(stringTokenizer.nextToken());
                    boolean isNonstop = Integer.valueOf(stringTokenizer.nextToken()) != 0;

                    return new BusTime(hour, minutes, week, isNonstop);
                }).toList();
    }
}
