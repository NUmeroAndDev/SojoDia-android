package com.numero.sojodia.helper;

import android.content.Context;
import android.content.res.AssetManager;

import com.numero.sojodia.model.BusTime;
import com.numero.sojodia.util.DateUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.StringTokenizer;

public class ParseHelper {

    public static void parse(Context context, String fileName, List<BusTime> timeListOnWeekday, List<BusTime> timeListOnSaturday, List<BusTime> timeListOnSunday) {
        timeListOnWeekday.clear();
        timeListOnSaturday.clear();
        timeListOnSaturday.clear();
        try {
            BufferedReader bufferedReader;
            FileInputStream fileInputStream = null;
            InputStream inputStream = null;

            File file = new File(context.getFilesDir() + "/" + fileName);
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
            } else {
                AssetManager assetManager = context.getResources().getAssets();
                inputStream = assetManager.open(fileName);
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            }

            String tmp;
            boolean isFirstLine = true;
            while ((tmp = bufferedReader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                StringTokenizer stringTokenizer = new StringTokenizer(tmp, ",");

                int hourValue = Integer.valueOf(stringTokenizer.nextToken());
                int minutesValue = Integer.valueOf(stringTokenizer.nextToken());
                int weekValue = Integer.valueOf(stringTokenizer.nextToken());
                boolean isNonstop = Integer.valueOf(stringTokenizer.nextToken()) != 0 ? true : false;

                BusTime busTime = new BusTime(hourValue, minutesValue, weekValue, isNonstop);
                switch (weekValue) {
                    case DateUtil.WEEKDAY:
                        timeListOnWeekday.add(busTime);
                        break;
                    case DateUtil.SATURDAY:
                        timeListOnSaturday.add(busTime);
                        break;
                    case DateUtil.SUNDAY:
                        timeListOnSunday.add(busTime);
                        break;
                }
            }

            bufferedReader.close();
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
