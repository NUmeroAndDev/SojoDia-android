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
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import io.reactivex.Observable;

public class BusDataRepository implements IBusDataRepository {

    private Context context;
    private List<BusTime> dataList;

    public BusDataRepository(Context context) {
        this.context = context;
    }

    @Override
    public void clearCache() {
        dataList.clear();
        dataList = null;
    }

    @Override
    public Observable<List<BusTime>> loadBusData(BusDataFile busDataFile) {
        if (dataList != null) {
            return Observable.create(e -> e.onNext(dataList));
        }
        return Observable.create(e -> {
            dataList = new ArrayList<>();
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

            String lineString;
            boolean isFirstLine = true;
            while ((lineString = bufferedReader.readLine()) != null) {
                if (isFirstLine) {
                    // 最初の行はデータではないためスキップ
                    isFirstLine = false;
                    continue;
                }
                StringTokenizer tokenizer = new StringTokenizer(lineString, ",");
                int hour = Integer.valueOf(tokenizer.nextToken());
                int minutes = Integer.valueOf(tokenizer.nextToken());
                int week = Integer.valueOf(tokenizer.nextToken());
                boolean isNonstop = Integer.valueOf(tokenizer.nextToken()) != 0;

                dataList.add(new BusTime(hour, minutes, week, isNonstop));
            }

            bufferedReader.close();
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            e.onNext(dataList);
        });
    }
}
