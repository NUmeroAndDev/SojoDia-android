package com.numero.sojodia;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.numero.sojodia.Model.Time;
import com.numero.sojodia.Model.TimeTableItem;
import com.numero.sojodia.Utils.Constants;
import com.numero.sojodia.Utils.Holiday;

import java.util.ArrayList;
import java.util.Calendar;

public class CountdownTask extends AsyncTask<Void, Void, Void> {

    private ArrayList<TimeTableItem> tkTimeList, tndTimeList;
    private Handler handler;
    private Time tkTime, tndTime, nowTime, nextTime;
    private Holiday holiday;
    private int tkPosition = 0, tndPosition = 0, nowDayOfMonth;
    private int reciprocating;

    private CallbackOnProgress callbackOnProgress;

    public CountdownTask(int reciprocating) {
        this.tkTimeList = new ArrayList<>();
        this.tndTimeList = new ArrayList<>();
        this.reciprocating = reciprocating;
        tkTime = new Time();
        tndTime = new Time();
        nowTime = new Time();
        nextTime = new Time();
        holiday = new Holiday();
        nowDayOfMonth = -1;

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                callbackOnProgress.onHandlerDateChanged(getTodayString());
                return false;
            }
        });
    }

    public void setCallbackOnProgress(CallbackOnProgress callback) {
        this.callbackOnProgress = callback;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            while (true) {
                if (isDateChanged()) {
                    TimeDataController.setTKTimeList(tkTimeList, reciprocating, getWeek());
                    TimeDataController.setTNDTimeList(tndTimeList, reciprocating, getWeek());
                    nowDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                    handler.sendMessage(Message.obtain());
                }
                if (this.isCancelled()) {
                    break;
                }
                nowTime.setNowTime();
                Thread.sleep(100);
                setTKPosition();
                setTNDPosition();
                publishProgress();
            }
        } catch (InterruptedException ignored) {
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... progress) {
        setTKTimes();
        setTNDTimes();
    }

    @Override
    protected void onCancelled() {
    }

    private void setTKTimes() {
        Time limitTime;
        String nextBusTimeString;
        String nextNextBusTimeString;
        String limitTimeString;
        int resColor;

        if (tkPosition == -1) {
            nextBusTimeString = "本日のバスはありません";
            nextNextBusTimeString = "";
            limitTimeString = "";
            resColor = R.color.textSecondary;
        } else if (tkPosition == tkTimeList.size() - 1) {
            tkTime.setTime(tkTimeList.get(tkPosition).hour, tkTimeList.get(tkPosition).min, 0);
            limitTime = Time.subtraction(tkTime, nowTime);

            nextBusTimeString = tkTime.getTimeString();
            nextNextBusTimeString = "最終バス";
            limitTimeString = limitTime.getTimeString();
            resColor = getLimitTimeTextResColor(limitTime.hour, limitTime.min);
        } else {
            tkTime.setTime(tkTimeList.get(tkPosition).hour, tkTimeList.get(tkPosition).min, 0);
            nextTime.setTime(tkTimeList.get(tkPosition + 1).hour, tkTimeList.get(tkPosition + 1).min, 0);
            limitTime = Time.subtraction(tkTime, nowTime);

            nextBusTimeString = tkTime.getTimeString();
            nextNextBusTimeString = "→" + nextTime.getTimeString();
            limitTimeString = limitTime.getTimeString();
            resColor = getLimitTimeTextResColor(limitTime.hour, limitTime.min);
        }

        callbackOnProgress.onProgressTK(nextBusTimeString, nextNextBusTimeString, limitTimeString, resColor);
    }

    private void setTNDTimes() {
        Time limitTime;
        String nextBusTimeString;
        String nextNextBusTimeString;
        String limitTimeString;
        int resColor;

        if (tndPosition == -1) {
            nextBusTimeString = "本日のバスはありません";
            nextNextBusTimeString = "";
            limitTimeString = "";
            resColor = R.color.textSecondary;
        } else if (tndPosition == tndTimeList.size() - 1) {
            tndTime.setTime(tndTimeList.get(tndPosition).hour, tndTimeList.get(tndPosition).min, 0);
            limitTime = Time.subtraction(tndTime, nowTime);

            nextBusTimeString = tndTime.getTimeString();
            nextNextBusTimeString = "最終バス";
            limitTimeString = limitTime.getTimeString();
            resColor = getLimitTimeTextResColor(limitTime.hour, limitTime.min);
        } else {
            tndTime.setTime(tndTimeList.get(tndPosition).hour, tndTimeList.get(tndPosition).min, 0);
            nextTime.setTime(tndTimeList.get(tndPosition + 1).hour, tndTimeList.get(tndPosition + 1).min, 0);
            limitTime = Time.subtraction(tndTime, nowTime);

            nextBusTimeString = tndTime.getTimeString();
            nextNextBusTimeString = "→" + nextTime.getTimeString();
            limitTimeString = limitTime.getTimeString();
            resColor = getLimitTimeTextResColor(limitTime.hour, limitTime.min);
        }

        callbackOnProgress.onProgressTND(nextBusTimeString, nextNextBusTimeString, limitTimeString, resColor);
    }

    private void setTKPosition() {
        if (tkPosition != -1 && Time.isOverTime(tkTime, nowTime)) {
            return;
        }
        for (tkPosition = 0; tkPosition < tkTimeList.size(); tkPosition++) {
            tkTime.setTime(tkTimeList.get(tkPosition).hour, tkTimeList.get(tkPosition).min, 0);
            if (Time.isOverTime(tkTime, nowTime)) {
                break;
            }
        }
        if (tkPosition >= tkTimeList.size()) {
            tkPosition = -1;
        }
    }

    private void setTNDPosition() {
        if (tndPosition != -1 && Time.isOverTime(tndTime, nowTime)) {
            return;
        }
        for (tndPosition = 0; tndPosition < tndTimeList.size(); tndPosition++) {
            tndTime.setTime(tndTimeList.get(tndPosition).hour, tndTimeList.get(tndPosition).min, 0);
            if (Time.isOverTime(tndTime, nowTime)) {
                break;
            }
        }
        if (tndPosition >= tndTimeList.size()) {
            tndPosition = -1;
        }
    }

    private boolean isDateChanged() {
        return (nowDayOfMonth != Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    private int getWeek() {
        holiday.setToday();
        if (holiday.isHoliday()) {
            return (holiday.isSchool() ? TimeDataController.HOLIDAY_IN_SCHOOL : TimeDataController.SUNDAY);
        } else {
            switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                    return TimeDataController.SUNDAY;
                case Calendar.SATURDAY:
                    return TimeDataController.SATURDAY;
                default:
                    return TimeDataController.WEEKDAY;
            }
        }
    }

    private String getTodayString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        String yearString = String.valueOf(year);
        String monthString = month < 10 ? "0" + String.valueOf(month) : String.valueOf(month);
        String dayString = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
        String weekStrings[] = {"日", "月", "火", "水", "木", "金", "土"};
        String holidayStrings[] = {"", "", "", "・祝"};

        if (getWeek() == Constants.SUNDAY && holiday.isHoliday()) {
            return yearString + "/" + monthString + "/" + dayString + "(" + weekStrings[week - 1] + holidayStrings[3] + ")";
        }

        return yearString + "/" + monthString + "/" + dayString + "(" + weekStrings[week - 1] + holidayStrings[getWeek()] + ")";
    }

    private int getLimitTimeTextResColor(int hour, int min) {
        if (nowTime.sec % 2 == 0) {
            return R.color.textSecondary;
        }
        if (hour == 0 && min <= 4) {
            return R.color.red;
        } else {
            return R.color.blue;
        }
    }

    interface CallbackOnProgress {
        void onProgressTK(String nextBusTimeString, String nextNextBusTimeString, String limitTimeString, int resColor);

        void onProgressTND(String nextBusTimeString, String nextNextBusTimeString, String limitTimeString, int resColor);

        void onHandlerDateChanged(String todayDateString);
    }

}
