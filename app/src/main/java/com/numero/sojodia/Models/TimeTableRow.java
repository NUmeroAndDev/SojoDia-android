package com.numero.sojodia.Models;

public class TimeTableRow {

    public String hourString, timeWeekday, timeSaturday, timeSunday, timeHolidayInSchool;

    public TimeTableRow(){
        hourString = "";
        timeWeekday = "";
        timeSaturday = "";
        timeSunday = "";
        timeHolidayInSchool = "";
    }

    public void setHourString(String str){
        this.hourString = str;
    }

    public void addStringTimeOnWeekday(String str){
        timeWeekday += str + " ";
    }

    public void addStringTimeOnSaturday(String str){
        timeSaturday += str + " ";
    }

    public void addStringTimeOnSunday(String str){
        timeSunday += str + " ";
    }

    public void addStringTimeOnHoliday(String str){
        timeHolidayInSchool += str + " ";
    }
}
