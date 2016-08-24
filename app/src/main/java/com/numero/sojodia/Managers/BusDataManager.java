package com.numero.sojodia.Managers;

import android.content.Context;
import android.content.res.AssetManager;

import com.numero.sojodia.Models.BusTime;
import com.numero.sojodia.Utils.ApplicationPreferences;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BusDataManager {

    public static final int WEEKDAY = 0;
    public static final int SATURDAY = 1;
    public static final int SUNDAY = 2;
    public static final int HOLIDAY_IN_SCHOOL = 3;
    public static final int ALL = 4;

    public final static int RECIPROCATE_GOING = 0;
    public final static int RECIPROCATE_RETURN = 1;

    public static void initBusDataTK(Context context, ArrayList<BusTime> timeList, int reciprocating, int week){
        timeList.clear();
        switch (reciprocating) {
            case RECIPROCATE_GOING:
                parseBusData(context, "TkToKutc.csv", timeList, week);
                break;
            case RECIPROCATE_RETURN:
                parseBusData(context, "KutcToTk.csv", timeList, week);
                break;
        }
    }

    public static void initBusDataTND(Context context, ArrayList<BusTime> timeList, int reciprocating, int week){
        timeList.clear();
        switch (reciprocating) {
            case RECIPROCATE_GOING:
                parseBusData(context, "TndToKutc.csv", timeList, week);
                break;
            case RECIPROCATE_RETURN:
                parseBusData(context, "KutcToTnd.csv", timeList, week);
                break;
        }
    }


    private static void parseBusData(Context context, String fileName, ArrayList<BusTime> timeList, int week){
        if (ApplicationPreferences.getVersionCode(context) == 20160401){
            parseFromAssets(context, fileName, timeList, week);
        } else {
            parseFromDownloadedFiles(context, fileName, timeList, week);
        }
    }

    private static void parseFromAssets(Context context, String fileName, ArrayList<BusTime> timeList, int week){
        try {
            AssetManager assetManager = context.getResources().getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String tmp;
            boolean isFirstLine = true;
            while ((tmp = bufferedReader.readLine()) != null) {
                if (isFirstLine){
                    isFirstLine = false;
                    continue;
                }

                StringTokenizer stringTokenizer = new StringTokenizer(tmp, ",");

                int hourValue = Integer.valueOf(stringTokenizer.nextToken());
                int minutesValue = Integer.valueOf(stringTokenizer.nextToken());
                int weekValue = Integer.valueOf(stringTokenizer.nextToken());

                if (week == ALL || weekValue == week) {
                    timeList.add(new BusTime(hourValue, minutesValue, weekValue));
                }
            }

            bufferedReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseFromDownloadedFiles(Context context, String fileName, ArrayList<BusTime> timeList, int week){
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));

            String tmp;
            boolean isFirstLine = true;
            while ((tmp = bufferedReader.readLine()) != null) {
                if (isFirstLine){
                    isFirstLine = false;
                    continue;
                }
                StringTokenizer stringTokenizer = new StringTokenizer(tmp, ",");

                int hourValue = Integer.valueOf(stringTokenizer.nextToken());
                int minutesValue = Integer.valueOf(stringTokenizer.nextToken());
                int weekValue = Integer.valueOf(stringTokenizer.nextToken());

                if (week == ALL || weekValue == week) {
                    timeList.add(new BusTime(hourValue, minutesValue, weekValue));
                }
            }

            bufferedReader.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
