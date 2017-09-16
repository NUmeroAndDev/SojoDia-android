package com.numero.sojodia.model;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class TimeTableRow {

    private String hourText;

    private StringBuilder timeOnWeekdayStringBuilder = new StringBuilder();
    private StringBuilder timeOnSaturdayStringBuilder = new StringBuilder();
    private StringBuilder timeOnSundayStringBuilder = new StringBuilder();

    public TimeTableRow() {
    }

    public void setHour(int hour) {
        this.hourText = String.format("%02d", hour);
    }

    public String getHourText() {
        return hourText;
    }

    public void addTimeOnWeekday(int time, boolean isNonStop) {
        if (isNonStop) {
            timeOnWeekdayStringBuilder.append("★");
        }
        timeOnWeekdayStringBuilder.append(String.format("%02d ", time));
    }

    public String getTimeOnWeekdayText() {
        return timeOnWeekdayStringBuilder.toString();
    }

    public void addTimeOnSaturday(int time, boolean isNonStop) {
        if (isNonStop) {
            timeOnSaturdayStringBuilder.append("★");
        }
        timeOnSaturdayStringBuilder.append(String.format("%02d ", time));
    }

    public String getTimeOnSaturdayText() {
        return timeOnSaturdayStringBuilder.toString();
    }

    public void addTimeOnSunday(int time, boolean isNonStop) {
        if (isNonStop) {
            timeOnSundayStringBuilder.append("★");
        }
        timeOnSundayStringBuilder.append(String.format("%02d ", time));
    }

    public String getTimeOnSundayText() {
        return timeOnSundayStringBuilder.toString();
    }
}
