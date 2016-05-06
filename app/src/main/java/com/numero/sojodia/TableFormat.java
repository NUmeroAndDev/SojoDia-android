package com.numero.sojodia;

public class TableFormat {
    String time, timeWeekday, timeSaturday, timeSunday, timeSundaySp;

    TableFormat(){
        time = "";
        timeWeekday = "";
        timeSaturday = "";
        timeSunday = "";
        timeSundaySp = "";
    }

    public void setTime(String str){
        this.time = str;
    }

    public void addTimeWeekday(String str){
        timeWeekday += str + " ";
    }

    public void addTimeSaturday(String str){
        timeSaturday += str + " ";
    }

    public void addTimeSunday(String str){
        timeSunday += str + " ";
    }

    public void addTimeSundaySp(String str){
        timeSundaySp += str + " ";
    }
}
