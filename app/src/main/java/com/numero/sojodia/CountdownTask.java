package com.numero.sojodia;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.numero.sojodia.Model.Time;
import com.numero.sojodia.Model.TimeTableItem;
import com.numero.sojodia.Utils.Holiday;

import java.util.ArrayList;
import java.util.Calendar;

public class CountdownTask extends AsyncTask<Void, Void, Void> {

    private Context context;
<<<<<<< HEAD:app/src/main/java/com/numero/sojodia/ViewTask.java
    private TimeList timeList;
    private ArrayList<TimeFormat> tkTimeList, tndTimeList;
    private TextView tkTimeText[], tndTimeText[], day;
=======
    private TimeData timeData;
    private ArrayList<TimeTableItem> tkTimeList, tndTimeList;
    private TextView tkTimeText[], tndTimeText[], dateTextView;
>>>>>>> editing:app/src/main/java/com/numero/sojodia/CountdownTask.java
    private Time tkTime, tndTime, nowTime, nextTime;
    private Holiday holiday;
    private int tkPosition = 0, tndPosition = 0, nowDay;
    private boolean isTimeDataReload = true;
    private String date;
    private int round;

    public CountdownTask(Context context, TextView tkTimeText[], TextView tndTimeText[], TextView dateTextView, int round) {
        this.context = context;
        this.tkTimeList = new ArrayList<>();
        this.tndTimeList = new ArrayList<>();
        this.tkTimeText = tkTimeText;
        this.tndTimeText = tndTimeText;
        this.dateTextView = dateTextView;
        this.round = round;
        timeData = new TimeData();
        tkTime = new Time();
        tndTime = new Time();
        nowTime = new Time();
        nextTime = new Time();
        holiday = new Holiday();
        nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            while (true) {
                if (isTimeDataReload) {
                    timeData.setTK(tkTimeList, getWeek(), round);
                    timeData.setTND(tndTimeList, getWeek(), round);
                    date = setDate();
                    nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                    isTimeDataReload = false;
                }
                if (this.isCancelled()) {
                    break;
                }
                Thread.sleep(100);
                setPosition();
                checkChangeDate();
                publishProgress();
            }
        } catch (InterruptedException ignored) {
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... progress) {
        nowTime.setTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND));

        if (tkPosition == -1) {
            tkTimeText[0].setText("本日のバスはありません");
            tkTimeText[1].setText("");
            tkTimeText[2].setText("");
        } else if (tkPosition == tkTimeList.size() - 1) {
            tkTimeText[1].setText("最終バス");
            tkTime.setTime(tkTimeList.get(tkPosition).hour, tkTimeList.get(tkPosition).min, 0);
            nowTime.setTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND));
            nowTime.minus(tkTime);
            tkTimeText[0].setText(tkTime.getTime());
            tkTimeText[2].setText(nowTime.getTime());
            changeColorTextView(tkTimeText[2], nowTime.hour, nowTime.min);
        } else {
            tkTime.setTime(tkTimeList.get(tkPosition).hour, tkTimeList.get(tkPosition).min, 0);
            nextTime.setTime(tkTimeList.get(tkPosition + 1).hour, tkTimeList.get(tkPosition + 1).min, 0);
            nowTime.setTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND));
            nowTime.minus(tkTime);
            tkTimeText[0].setText(tkTime.getTime());
            tkTimeText[1].setText("→" + nextTime.getTime());
            tkTimeText[2].setText(nowTime.getTime());
            changeColorTextView(tkTimeText[2], nowTime.hour, nowTime.min);
        }
        if (tndPosition == -1) {
            tndTimeText[0].setText("本日のバスはありません");
            tndTimeText[1].setText("");
            tndTimeText[2].setText("");
        } else if (tndPosition == tndTimeList.size() - 1) {
            tndTimeText[1].setText("最終バス");
            tndTime.setTime(tndTimeList.get(tndPosition).hour, tndTimeList.get(tndPosition).min, 0);
            nowTime.setTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND));
            nowTime.minus(tndTime);
            tndTimeText[0].setText(tndTime.getTime());
            tndTimeText[2].setText(nowTime.getTime());
            changeColorTextView(tndTimeText[2], nowTime.hour, nowTime.min);
        } else {
            tndTime.setTime(tndTimeList.get(tndPosition).hour, tndTimeList.get(tndPosition).min, 0);
            nextTime.setTime(tndTimeList.get(tndPosition + 1).hour, tndTimeList.get(tndPosition + 1).min, 0);
            nowTime.setTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND));
            nowTime.minus(tndTime);
            tndTimeText[0].setText(tndTime.getTime());
            tndTimeText[1].setText("→" + nextTime.getTime());
            tndTimeText[2].setText(nowTime.getTime());
            changeColorTextView(tndTimeText[2], nowTime.hour, nowTime.min);
        }
        dateTextView.setText(date);
    }

    @Override
    protected void onCancelled() {
    }

    private void setPosition() {
        nowTime.setTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND));

        for (tkPosition = 0; tkPosition < tkTimeList.size(); tkPosition++) {
            tkTime.setTime(tkTimeList.get(tkPosition).hour, tkTimeList.get(tkPosition).min, 0);
            if (tkTime.isMinus(nowTime)) {
                break;
            }
        }
        if (tkPosition >= tkTimeList.size()) {
            tkPosition = -1;
        }
        for (tndPosition = 0; tndPosition < tndTimeList.size(); tndPosition++) {
            tndTime.setTime(tndTimeList.get(tndPosition).hour, tndTimeList.get(tndPosition).min, 0);
            if (tndTime.isMinus(nowTime)) {
                break;
            }
        }
        if (tndPosition >= tndTimeList.size()) {
            tndPosition = -1;
        }
    }

    private void checkChangeDate() {
        isTimeDataReload = (nowDay != Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    private int getWeek() {
        holiday.setToday();
        if (holiday.isHoliday()) {
            return (holiday.isSchool() ? 3 : 2);
        } else {
            switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    return 2;
                case 7:
                    return 1;
                default:
                    return 0;
            }
        }
    }

    private String setDate() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        String weekStrings[] = {"日", "月", "火", "水", "木", "金", "土"};
        String holidayStrings[] = {"", "", "", "・祝"};

        if (getWeek() == 2 && holiday.isHoliday()) {
            return "" + year + "/" + (month + 1) + "/" + day + "(" + weekStrings[week - 1] + holidayStrings[3] + ")";
        }

        return "" + year + "/" + (month + 1) + "/" + day + "(" + weekStrings[week - 1] + holidayStrings[getWeek()] + ")";
    }

    private void changeColorTextView(TextView textView, int hour, int min) {
        if (Calendar.getInstance().get(Calendar.SECOND) % 2 == 0) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.textSecondary));
            return;
        }
        if (hour == 0 && min <= 4) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.blue));
        }
    }
}
