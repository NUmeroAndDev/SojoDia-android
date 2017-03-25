package com.numero.sojodia.model;

public class TimeTableRow {

    public String hourText;
    public String timeTextOnWeekday;
    public String timeTextOnSaturday;
    public String timeTextOnSunday;
    public String timeTextOnHolidayInSchool;

    public TimeTableRow() {
        hourText = "";
        timeTextOnWeekday = "";
        timeTextOnSaturday = "";
        timeTextOnSunday = "";
        timeTextOnHolidayInSchool = "";
    }

    public void setHourText(String text) {
        this.hourText = text;
    }

    public void addTimeTextOnWeekday(String text) {
        timeTextOnWeekday += String.format("%s ", text);
    }

    public void addTimeTextOnSaturday(String text) {
        timeTextOnSaturday += String.format("%s ", text);
    }

    public void addTimeTextOnSunday(String text) {
        timeTextOnSunday += String.format("%s ", text);
    }

    public void addTimeTextOnHolidayInSchool(String text) {
        timeTextOnHolidayInSchool += String.format("%s ", text);
    }
}
