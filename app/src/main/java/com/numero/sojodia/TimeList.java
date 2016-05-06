package com.numero.sojodia;

import java.util.ArrayList;

public class TimeList {

    ArrayList<TimeTableFormat> list;

    TimeList() {
    }

    public void setTK(ArrayList<TimeTableFormat> list, int week, int round){
        this.list = list;
        this.list.clear();
        switch (round){
            case 0:
                setArrivalTK(week);
                break;
            case 1:
                setDepartureTK(week);
                break;
        }
    }

    public void setTND(ArrayList<TimeTableFormat> list, int week, int round){
        this.list = list;
        this.list.clear();
        switch (round){
            case 0:
                setArrivalTND(week);
                break;
            case 1:
                setDepartureTND(week);
                break;
        }
    }

    public void setAll(ArrayList<TimeTableFormat> list, int route, int round){
        this.list = list;
        this.list.clear();
        switch (round){
            case 0:
                for(int i = 0; i < 4; i++) {
                    if(route == Constants.ROUTE_TK) {
                        setArrivalTK(i);
                    }else{
                        setArrivalTND(i);
                    }
                }
                break;
            case 1:
                for(int i = 0; i < 4; i++) {
                    if(route == Constants.ROUTE_TK) {
                        setDepartureTK(i);
                    }else{
                        setDepartureTND(i);
                    }
                }
                break;
        }
    }

    //高槻→関大
    private void setArrivalTK(int week){
        switch (week){
            case 0:
                //平日
                list.add(new TimeTableFormat(6, 35, 0, 0));
                list.add(new TimeTableFormat(7, 23, 0, 0));
                list.add(new TimeTableFormat(7, 55, 0, 0));
                list.add(new TimeTableFormat(8, 8, 0, 0));
                list.add(new TimeTableFormat(8, 13, 0, 0));
                list.add(new TimeTableFormat(8, 18, 0, 0));
                list.add(new TimeTableFormat(8, 22, 0, 0));
                list.add(new TimeTableFormat(8, 26, 0, 0));
                list.add(new TimeTableFormat(8, 29, 0, 0));
                list.add(new TimeTableFormat(8, 32, 0, 0));
                list.add(new TimeTableFormat(8, 35, 0, 0));
                list.add(new TimeTableFormat(8, 40, 0, 0));
                list.add(new TimeTableFormat(8, 45, 0, 0));
                list.add(new TimeTableFormat(8, 55, 0, 0));
                list.add(new TimeTableFormat(9, 15, 0, 0));
                list.add(new TimeTableFormat(9, 25, 0, 0));
                list.add(new TimeTableFormat(9, 35, 0, 0));
                list.add(new TimeTableFormat(9, 47, 0, 0));
                list.add(new TimeTableFormat(9, 55, 0, 0));
                list.add(new TimeTableFormat(10, 0, 0, 0));
                list.add(new TimeTableFormat(10, 5, 0, 0));
                list.add(new TimeTableFormat(10, 10, 0, 0));
                list.add(new TimeTableFormat(10, 15, 0, 0));
                list.add(new TimeTableFormat(10, 25, 0, 0));
                list.add(new TimeTableFormat(10, 35, 0, 0));
                list.add(new TimeTableFormat(10, 43, 0, 0));
                list.add(new TimeTableFormat(10, 55, 0, 0));
                list.add(new TimeTableFormat(11, 15, 0, 0));
                list.add(new TimeTableFormat(11, 35, 0, 0));
                list.add(new TimeTableFormat(11, 50, 0, 0));
                list.add(new TimeTableFormat(12, 5, 0, 0));
                list.add(new TimeTableFormat(12, 20, 0, 0));
                list.add(new TimeTableFormat(12, 30, 0, 0));
                list.add(new TimeTableFormat(12, 35, 0, 0));
                list.add(new TimeTableFormat(12, 50, 0, 0));
                list.add(new TimeTableFormat(13, 10, 0, 0));
                list.add(new TimeTableFormat(13, 35, 0, 0));
                list.add(new TimeTableFormat(13, 50, 0, 0));
                list.add(new TimeTableFormat(14, 5, 0, 0));
                list.add(new TimeTableFormat(14, 20, 0, 0));
                list.add(new TimeTableFormat(14, 40, 0, 0));
                list.add(new TimeTableFormat(15, 5, 0, 0));
                list.add(new TimeTableFormat(15, 20, 0, 0));
                list.add(new TimeTableFormat(15, 35, 0, 0));
                list.add(new TimeTableFormat(15, 50, 0, 0));
                list.add(new TimeTableFormat(16, 5, 0, 0));
                list.add(new TimeTableFormat(16, 20, 0, 0));
                list.add(new TimeTableFormat(16, 35, 0, 0));
                list.add(new TimeTableFormat(16, 50, 0, 0));
                list.add(new TimeTableFormat(17, 5, 0, 0));
                list.add(new TimeTableFormat(17, 20, 0, 0));
                list.add(new TimeTableFormat(17, 35, 0, 0));
                list.add(new TimeTableFormat(17, 50, 0, 0));
                list.add(new TimeTableFormat(18, 5, 0, 0));
                list.add(new TimeTableFormat(18, 20, 0, 0));
                list.add(new TimeTableFormat(18, 35, 0, 0));
                list.add(new TimeTableFormat(18, 50, 0, 0));
                list.add(new TimeTableFormat(19, 5, 0, 0));
                list.add(new TimeTableFormat(19, 20, 0, 0));
                list.add(new TimeTableFormat(19, 35, 0, 0));
                list.add(new TimeTableFormat(19, 50, 0, 0));
                list.add(new TimeTableFormat(20, 10, 0, 0));
                list.add(new TimeTableFormat(20, 30, 0, 0));
                list.add(new TimeTableFormat(20, 50, 0, 0));
                list.add(new TimeTableFormat(21, 20, 0, 0));
                list.add(new TimeTableFormat(21, 50, 0, 0));
                list.add(new TimeTableFormat(23, 1, 0, 0));
                break;
            case 1:
                //土曜
                list.add(new TimeTableFormat(8, 0, 0, 1));
                list.add(new TimeTableFormat(8, 14, 0, 1));
                list.add(new TimeTableFormat(8, 28, 0, 1));
                list.add(new TimeTableFormat(8, 44, 0, 1));
                list.add(new TimeTableFormat(9, 2, 0, 1));
                list.add(new TimeTableFormat(9, 20, 0, 1));
                list.add(new TimeTableFormat(9, 38, 0, 1));
                list.add(new TimeTableFormat(9, 55, 0, 1));
                list.add(new TimeTableFormat(10, 10, 0, 1));
                list.add(new TimeTableFormat(10, 30, 0, 1));
                list.add(new TimeTableFormat(10, 50, 0, 1));
                list.add(new TimeTableFormat(11, 10, 0, 1));
                list.add(new TimeTableFormat(11, 30, 0, 1));
                list.add(new TimeTableFormat(11, 50, 0, 1));
                list.add(new TimeTableFormat(12, 10, 0, 1));
                list.add(new TimeTableFormat(12, 30, 0, 1));
                list.add(new TimeTableFormat(12, 50, 0, 1));
                list.add(new TimeTableFormat(13, 10, 0, 1));
                list.add(new TimeTableFormat(13, 30, 0, 1));
                list.add(new TimeTableFormat(13, 50, 0, 1));
                list.add(new TimeTableFormat(14, 10, 0, 1));
                list.add(new TimeTableFormat(14, 30, 0, 1));
                list.add(new TimeTableFormat(14, 50, 0, 1));
                list.add(new TimeTableFormat(15, 10, 0, 1));
                list.add(new TimeTableFormat(15, 30, 0, 1));
                list.add(new TimeTableFormat(15, 50, 0, 1));
                list.add(new TimeTableFormat(16, 10, 0, 1));
                list.add(new TimeTableFormat(16, 30, 0, 1));
                list.add(new TimeTableFormat(16, 50, 0, 1));
                list.add(new TimeTableFormat(17, 10, 0, 1));
                list.add(new TimeTableFormat(17, 30, 0, 1));
                list.add(new TimeTableFormat(17, 50, 0, 1));
                list.add(new TimeTableFormat(21, 0, 0, 1));
                break;
            case 2:
                //日祝
                list.add(new TimeTableFormat(8, 5, 0, 2));
                list.add(new TimeTableFormat(8, 33, 0, 2));
                list.add(new TimeTableFormat(8, 50, 0, 2));
                list.add(new TimeTableFormat(9, 10, 0, 2));
                list.add(new TimeTableFormat(9, 40, 0, 2));
                list.add(new TimeTableFormat(10, 10, 0, 2));
                list.add(new TimeTableFormat(10, 40, 0, 2));
                list.add(new TimeTableFormat(11, 10, 0, 2));
                list.add(new TimeTableFormat(11, 40, 0, 2));
                list.add(new TimeTableFormat(12, 10, 0, 2));
                list.add(new TimeTableFormat(12, 40, 0, 2));
                list.add(new TimeTableFormat(13, 10, 0, 2));
                list.add(new TimeTableFormat(13, 40, 0, 2));
                list.add(new TimeTableFormat(14, 10, 0, 2));
                list.add(new TimeTableFormat(14, 40, 0, 2));
                list.add(new TimeTableFormat(15, 10, 0, 2));
                list.add(new TimeTableFormat(15, 40, 0, 2));
                list.add(new TimeTableFormat(16, 10, 0, 2));
                list.add(new TimeTableFormat(16, 40, 0, 2));
                list.add(new TimeTableFormat(17, 10, 0, 2));
                break;
            case 3:
                //祝日登校
                list.add(new TimeTableFormat(8, 5, 0, 3));
                list.add(new TimeTableFormat(8, 10, 0, 3));
                list.add(new TimeTableFormat(8, 15, 0, 3));
                list.add(new TimeTableFormat(8, 20, 0, 3));
                list.add(new TimeTableFormat(8, 25, 0, 3));
                list.add(new TimeTableFormat(8, 30, 0, 3));
                list.add(new TimeTableFormat(8, 33, 0, 3));
                list.add(new TimeTableFormat(8, 36, 0, 3));
                list.add(new TimeTableFormat(8, 40, 0, 3));
                list.add(new TimeTableFormat(8, 50, 0, 3));
                list.add(new TimeTableFormat(8, 55, 0, 3));
                list.add(new TimeTableFormat(9, 10, 0, 3));
                list.add(new TimeTableFormat(9, 20, 0, 3));
                list.add(new TimeTableFormat(9, 30, 0, 3));
                list.add(new TimeTableFormat(9, 40, 0, 3));
                list.add(new TimeTableFormat(9, 50, 0, 3));
                list.add(new TimeTableFormat(9, 55, 0, 3));
                list.add(new TimeTableFormat(10, 0, 0, 3));
                list.add(new TimeTableFormat(10, 10, 0, 3));
                list.add(new TimeTableFormat(10, 20, 0, 3));
                list.add(new TimeTableFormat(10, 40, 0, 3));
                list.add(new TimeTableFormat(11, 10, 0, 3));
                list.add(new TimeTableFormat(11, 40, 0, 3));
                list.add(new TimeTableFormat(12, 0, 0, 3));
                list.add(new TimeTableFormat(12, 10, 0, 3));
                list.add(new TimeTableFormat(12, 20, 0, 3));
                list.add(new TimeTableFormat(12, 30, 0, 3));
                list.add(new TimeTableFormat(12, 40, 0, 3));
                list.add(new TimeTableFormat(13, 10, 0, 3));
                list.add(new TimeTableFormat(13, 40, 0, 3));
                list.add(new TimeTableFormat(14, 10, 0, 3));
                list.add(new TimeTableFormat(14, 40, 0, 3));
                list.add(new TimeTableFormat(15, 10, 0, 3));
                list.add(new TimeTableFormat(15, 40, 0, 3));
                list.add(new TimeTableFormat(16, 10, 0, 3));
                list.add(new TimeTableFormat(16, 40, 0, 3));
                list.add(new TimeTableFormat(17, 10, 0, 3));
                list.add(new TimeTableFormat(19, 10, 0, 3));
                break;
        }
    }

    //関大→高槻
    private void setDepartureTK(int week){
        switch (week){
            case 0:
                //平日
                list.add(new TimeTableFormat(7, 4, 1, 0));
                list.add(new TimeTableFormat(7, 33, 1, 0));
                list.add(new TimeTableFormat(8, 3, 1, 0));
                list.add(new TimeTableFormat(8, 48, 1, 0));
                list.add(new TimeTableFormat(9, 13, 1, 0));
                list.add(new TimeTableFormat(9, 28, 1, 0));
                list.add(new TimeTableFormat(10, 4, 1, 0));
                list.add(new TimeTableFormat(10, 23, 1, 0));
                list.add(new TimeTableFormat(10, 42, 1, 0));
                list.add(new TimeTableFormat(11, 3, 1, 0));
                list.add(new TimeTableFormat(11, 19, 1, 0));
                list.add(new TimeTableFormat(11, 35, 1, 0));
                list.add(new TimeTableFormat(11, 51, 1, 0));
                list.add(new TimeTableFormat(12, 10, 1, 0));
                list.add(new TimeTableFormat(12, 20, 1, 0));
                list.add(new TimeTableFormat(12, 30, 1, 0));
                list.add(new TimeTableFormat(12, 40, 1, 0));
                list.add(new TimeTableFormat(12, 50, 1, 0));
                list.add(new TimeTableFormat(13, 4, 1, 0));
                list.add(new TimeTableFormat(13, 24, 1, 0));
                list.add(new TimeTableFormat(13, 44, 1, 0));
                list.add(new TimeTableFormat(14, 4, 1, 0));
                list.add(new TimeTableFormat(14, 24, 1, 0));
                list.add(new TimeTableFormat(14, 37, 1, 0));
                list.add(new TimeTableFormat(14, 41, 1, 0));
                list.add(new TimeTableFormat(14, 45, 1, 0));
                list.add(new TimeTableFormat(14, 50, 1, 0));
                list.add(new TimeTableFormat(15, 9, 1, 0));
                list.add(new TimeTableFormat(15, 36, 1, 0));
                list.add(new TimeTableFormat(15, 51, 1, 0));
                list.add(new TimeTableFormat(16, 10, 1, 0));
                list.add(new TimeTableFormat(16, 15, 1, 0));
                list.add(new TimeTableFormat(16, 20, 1, 0));
                list.add(new TimeTableFormat(16, 24, 1, 0));
                list.add(new TimeTableFormat(16, 28, 1, 0));
                list.add(new TimeTableFormat(16, 36, 1, 0));
                list.add(new TimeTableFormat(16, 51, 1, 0));
                list.add(new TimeTableFormat(17, 5, 1, 0));
                list.add(new TimeTableFormat(17, 15, 1, 0));
                list.add(new TimeTableFormat(17, 25, 1, 0));
                list.add(new TimeTableFormat(17, 43, 1, 0));
                list.add(new TimeTableFormat(17, 55, 1, 0));
                list.add(new TimeTableFormat(17, 59, 1, 0));
                list.add(new TimeTableFormat(18, 3, 1, 0));
                list.add(new TimeTableFormat(18, 8, 1, 0));
                list.add(new TimeTableFormat(18, 14, 1, 0));
                list.add(new TimeTableFormat(18, 34, 1, 0));
                list.add(new TimeTableFormat(18, 50, 1, 0));
                list.add(new TimeTableFormat(19, 10, 1, 0));
                list.add(new TimeTableFormat(19, 40, 1, 0));
                list.add(new TimeTableFormat(19, 55, 1, 0));
                list.add(new TimeTableFormat(20, 10, 1, 0));
                list.add(new TimeTableFormat(20, 35, 1, 0));
                list.add(new TimeTableFormat(21, 20, 1, 0));
                list.add(new TimeTableFormat(21, 49, 1, 0));
                list.add(new TimeTableFormat(22, 19, 1, 0));
                break;
            case 1:
                //土曜
                list.add(new TimeTableFormat(7, 49, 1, 1));
                list.add(new TimeTableFormat(8, 19, 1, 1));
                list.add(new TimeTableFormat(8, 39, 1, 1));
                list.add(new TimeTableFormat(8, 58, 1, 1));
                list.add(new TimeTableFormat(9, 17, 1, 1));
                list.add(new TimeTableFormat(9, 37, 1, 1));
                list.add(new TimeTableFormat(9, 57, 1, 1));
                list.add(new TimeTableFormat(10, 17, 1, 1));
                list.add(new TimeTableFormat(10, 37, 1, 1));
                list.add(new TimeTableFormat(10, 57, 1, 1));
                list.add(new TimeTableFormat(11, 17, 1, 1));
                list.add(new TimeTableFormat(11, 37, 1, 1));
                list.add(new TimeTableFormat(11, 57, 1, 1));
                list.add(new TimeTableFormat(12, 17, 1, 1));
                list.add(new TimeTableFormat(12, 37, 1, 1));
                list.add(new TimeTableFormat(12, 57, 1, 1));
                list.add(new TimeTableFormat(13, 17, 1, 1));
                list.add(new TimeTableFormat(13, 37, 1, 1));
                list.add(new TimeTableFormat(13, 57, 1, 1));
                list.add(new TimeTableFormat(14, 17, 1, 1));
                list.add(new TimeTableFormat(14, 37, 1, 1));
                list.add(new TimeTableFormat(14, 57, 1, 1));
                list.add(new TimeTableFormat(15, 17, 1, 1));
                list.add(new TimeTableFormat(15, 37, 1, 1));
                list.add(new TimeTableFormat(15, 57, 1, 1));
                list.add(new TimeTableFormat(16, 17, 1, 1));
                list.add(new TimeTableFormat(16, 37, 1, 1));
                list.add(new TimeTableFormat(16, 57, 1, 1));
                list.add(new TimeTableFormat(17, 17, 1, 1));
                list.add(new TimeTableFormat(17, 37, 1, 1));
                list.add(new TimeTableFormat(17, 57, 1, 1));
                list.add(new TimeTableFormat(19, 44, 1, 1));
                list.add(new TimeTableFormat(21, 30, 1, 1));
                break;
            case 2:
                //日曜
                list.add(new TimeTableFormat(7, 59, 1, 2));
                list.add(new TimeTableFormat(8, 41, 1, 2));
                list.add(new TimeTableFormat(9, 9, 1, 2));
                list.add(new TimeTableFormat(9, 41, 1, 2));
                list.add(new TimeTableFormat(10, 11, 1, 2));
                list.add(new TimeTableFormat(10, 41, 1, 2));
                list.add(new TimeTableFormat(11, 11, 1, 2));
                list.add(new TimeTableFormat(11, 41, 1, 2));
                list.add(new TimeTableFormat(12, 11, 1, 2));
                list.add(new TimeTableFormat(12, 41, 1, 2));
                list.add(new TimeTableFormat(13, 11, 1, 2));
                list.add(new TimeTableFormat(13, 41, 1, 2));
                list.add(new TimeTableFormat(14, 11, 1, 2));
                list.add(new TimeTableFormat(14, 41, 1, 2));
                list.add(new TimeTableFormat(15, 11, 1, 2));
                list.add(new TimeTableFormat(15, 41, 1, 2));
                list.add(new TimeTableFormat(16, 11, 1, 2));
                list.add(new TimeTableFormat(16, 41, 1, 2));
                list.add(new TimeTableFormat(17, 11, 1, 2));
                break;
            case 3:
                //祝日登校
                list.add(new TimeTableFormat(7, 59, 1, 3));
                list.add(new TimeTableFormat(8, 41, 1, 3));
                list.add(new TimeTableFormat(9, 9, 1, 3));
                list.add(new TimeTableFormat(9, 41, 1, 3));
                list.add(new TimeTableFormat(10, 11, 1, 3));
                list.add(new TimeTableFormat(10, 41, 1, 3));
                list.add(new TimeTableFormat(11, 11, 1, 3));
                list.add(new TimeTableFormat(11, 41, 1, 3));
                list.add(new TimeTableFormat(11, 50, 1, 3));
                list.add(new TimeTableFormat(12, 11, 1, 3));
                list.add(new TimeTableFormat(12, 20, 1, 3));
                list.add(new TimeTableFormat(12, 30, 1, 3));
                list.add(new TimeTableFormat(12, 41, 1, 3));
                list.add(new TimeTableFormat(13, 11, 1, 3));
                list.add(new TimeTableFormat(13, 41, 1, 3));
                list.add(new TimeTableFormat(14, 11, 1, 3));
                list.add(new TimeTableFormat(14, 41, 1, 3));
                list.add(new TimeTableFormat(14, 45, 1, 3));
                list.add(new TimeTableFormat(14, 50, 1, 3));
                list.add(new TimeTableFormat(15, 11, 1, 3));
                list.add(new TimeTableFormat(15, 41, 1, 3));
                list.add(new TimeTableFormat(16, 11, 1, 3));
                list.add(new TimeTableFormat(16, 15, 1, 3));
                list.add(new TimeTableFormat(16, 25, 1, 3));
                list.add(new TimeTableFormat(16, 30, 1, 3));
                list.add(new TimeTableFormat(16, 41, 1, 3));
                list.add(new TimeTableFormat(17, 11, 1, 3));
                list.add(new TimeTableFormat(17, 20, 1, 3));
                list.add(new TimeTableFormat(17, 30, 1, 3));
                list.add(new TimeTableFormat(17, 40, 1, 3));
                list.add(new TimeTableFormat(17, 50, 1, 3));
                list.add(new TimeTableFormat(18, 0, 1, 3));
                list.add(new TimeTableFormat(18, 10, 1, 3));
                list.add(new TimeTableFormat(18, 20, 1, 3));
                list.add(new TimeTableFormat(18, 30, 1, 3));
                list.add(new TimeTableFormat(20, 0, 1, 3));
                break;
        }
    }

    //富田→関大
    private void setArrivalTND(int week) {
        switch (week) {
            case 0:
                //平日
                list.add(new TimeTableFormat(6, 20, 0, 0));
                list.add(new TimeTableFormat(7, 3, 0, 0));
                list.add(new TimeTableFormat(7, 29, 0, 0));
                list.add(new TimeTableFormat(8, 20, 0, 0));
                list.add(new TimeTableFormat(8, 25, 0, 0));
                list.add(new TimeTableFormat(8, 31, 0, 0));
                list.add(new TimeTableFormat(8, 34, 0, 0));
                list.add(new TimeTableFormat(8, 38, 0, 0));
                list.add(new TimeTableFormat(9, 35, 0, 0));
                list.add(new TimeTableFormat(9, 55, 0, 0));
                list.add(new TimeTableFormat(10, 10, 0, 0));
                list.add(new TimeTableFormat(10, 22, 0, 0));
                list.add(new TimeTableFormat(11, 10, 0, 0));
                list.add(new TimeTableFormat(11, 56, 0, 0));
                list.add(new TimeTableFormat(12, 30, 0, 0));
                list.add(new TimeTableFormat(13, 2, 0, 0));
                list.add(new TimeTableFormat(13, 22, 0, 0));
                list.add(new TimeTableFormat(13, 58, 0, 0));
                list.add(new TimeTableFormat(14, 15, 0, 0));
                list.add(new TimeTableFormat(14, 45, 0, 0));
                list.add(new TimeTableFormat(15, 10, 0, 0));
                list.add(new TimeTableFormat(15, 58, 0, 0));
                list.add(new TimeTableFormat(16, 30, 0, 0));
                list.add(new TimeTableFormat(16, 45, 0, 0));
                list.add(new TimeTableFormat(16, 59, 0, 0));
                list.add(new TimeTableFormat(17, 18, 0, 0));
                list.add(new TimeTableFormat(17, 38, 0, 0));
                list.add(new TimeTableFormat(18, 5, 0, 0));
                list.add(new TimeTableFormat(18, 40, 0, 0));
                list.add(new TimeTableFormat(19, 5, 0, 0));
                list.add(new TimeTableFormat(19, 35, 0, 0));
                list.add(new TimeTableFormat(20, 45, 0, 0));
                list.add(new TimeTableFormat(21, 50, 0, 0));
                break;
            case 1:
                //土曜
                list.add(new TimeTableFormat(6, 23, 0, 1));
                list.add(new TimeTableFormat(7, 5, 0, 1));
                list.add(new TimeTableFormat(7, 29, 0, 1));
                list.add(new TimeTableFormat(8, 30, 0, 1));
                list.add(new TimeTableFormat(9, 5, 0, 1));
                list.add(new TimeTableFormat(10, 5, 0, 1));
                list.add(new TimeTableFormat(11, 12, 0, 1));
                list.add(new TimeTableFormat(12, 1, 0, 1));
                list.add(new TimeTableFormat(12, 25, 0, 1));
                list.add(new TimeTableFormat(13, 7, 0, 1));
                list.add(new TimeTableFormat(14, 5, 0, 1));
                list.add(new TimeTableFormat(14, 39, 0, 1));
                list.add(new TimeTableFormat(15, 5, 0, 1));
                list.add(new TimeTableFormat(15, 47, 0, 1));
                list.add(new TimeTableFormat(16, 21, 0, 1));
                list.add(new TimeTableFormat(17, 30, 0, 1));
                list.add(new TimeTableFormat(18, 55, 0, 1));
                list.add(new TimeTableFormat(20, 10, 0, 1));
                list.add(new TimeTableFormat(21, 27, 0, 1));
                break;
            case 2:
                //日曜
                list.add(new TimeTableFormat(7, 5, 0, 2));
                list.add(new TimeTableFormat(8, 10, 0, 2));
                list.add(new TimeTableFormat(9, 5, 0, 2));
                list.add(new TimeTableFormat(9, 55, 0, 2));
                list.add(new TimeTableFormat(10, 55, 0, 2));
                list.add(new TimeTableFormat(11, 48, 0, 2));
                list.add(new TimeTableFormat(12, 55, 0, 2));
                list.add(new TimeTableFormat(13, 30, 0, 2));
                list.add(new TimeTableFormat(14, 5, 0, 2));
                list.add(new TimeTableFormat(15, 10, 0, 2));
                list.add(new TimeTableFormat(16, 21, 0, 2));
                list.add(new TimeTableFormat(17, 30, 0, 2));
                list.add(new TimeTableFormat(18, 50, 0, 2));
                list.add(new TimeTableFormat(19, 59, 0, 2));
                list.add(new TimeTableFormat(21, 21, 0, 2));
                break;
            case 3:
                //祝日登校
                list.add(new TimeTableFormat(7, 5, 0, 3));
                list.add(new TimeTableFormat(8, 10, 0, 3));
                list.add(new TimeTableFormat(8, 20, 0, 3));
                list.add(new TimeTableFormat(8, 30, 0, 3));
                list.add(new TimeTableFormat(9, 5, 0, 3));
                list.add(new TimeTableFormat(9, 30, 0, 3));
                list.add(new TimeTableFormat(9, 55, 0, 3));
                list.add(new TimeTableFormat(10, 10, 0, 3));
                list.add(new TimeTableFormat(10, 15, 0, 3));
                list.add(new TimeTableFormat(10, 55, 0, 3));
                list.add(new TimeTableFormat(11, 48, 0, 3));
                list.add(new TimeTableFormat(12, 30, 0, 3));
                list.add(new TimeTableFormat(12, 55, 0, 3));
                list.add(new TimeTableFormat(13, 30, 0, 3));
                list.add(new TimeTableFormat(14, 0, 0, 3));
                list.add(new TimeTableFormat(15, 10, 0, 3));
                list.add(new TimeTableFormat(16, 21, 0, 3));
                list.add(new TimeTableFormat(17, 30, 0, 3));
                list.add(new TimeTableFormat(18, 50, 0, 3));
                list.add(new TimeTableFormat(19, 59, 0, 3));
                list.add(new TimeTableFormat(21, 21, 0, 3));
                break;
        }
    }

    //関大→富田
    private void setDepartureTND(int week) {
        switch (week) {
            case 0:
                //平日
                list.add(new TimeTableFormat(6, 35, 1, 0));
                list.add(new TimeTableFormat(7, 1, 1, 0));
                list.add(new TimeTableFormat(7, 44, 1, 0));
                list.add(new TimeTableFormat(8, 10, 1, 0));
                list.add(new TimeTableFormat(9, 5, 1, 0));
                list.add(new TimeTableFormat(9, 18, 1, 0));
                list.add(new TimeTableFormat(9, 30, 1, 0));
                list.add(new TimeTableFormat(9, 45, 1, 0));
                list.add(new TimeTableFormat(10, 40, 1, 0));
                list.add(new TimeTableFormat(11, 3, 1, 0));
                list.add(new TimeTableFormat(11, 40, 1, 0));
                list.add(new TimeTableFormat(12, 39, 1, 0));
                list.add(new TimeTableFormat(13, 7, 1, 0));
                list.add(new TimeTableFormat(13, 43, 1, 0));
                list.add(new TimeTableFormat(14, 41, 1, 0));
                list.add(new TimeTableFormat(15, 20, 1, 0));
                list.add(new TimeTableFormat(16, 25, 1, 0));
                list.add(new TimeTableFormat(16, 31, 1, 0));
                list.add(new TimeTableFormat(16, 49, 1, 0));
                list.add(new TimeTableFormat(17, 10, 1, 0));
                list.add(new TimeTableFormat(17, 38, 1, 0));
                list.add(new TimeTableFormat(18, 1, 1, 0));
                list.add(new TimeTableFormat(18, 10, 1, 0));
                list.add(new TimeTableFormat(18, 30, 1, 0));
                list.add(new TimeTableFormat(19, 36, 1, 0));
                list.add(new TimeTableFormat(19, 45, 1, 0));
                list.add(new TimeTableFormat(20, 16, 1, 0));
                list.add(new TimeTableFormat(20, 45, 1, 0));
                list.add(new TimeTableFormat(21, 30, 1, 0));
                list.add(new TimeTableFormat(22, 31, 1, 0));
                break;
            case 1:
                //土曜
                list.add(new TimeTableFormat(7, 4, 1, 1));
                list.add(new TimeTableFormat(7, 46, 1, 1));
                list.add(new TimeTableFormat(8, 10, 1, 1));
                list.add(new TimeTableFormat(9, 14, 1, 1));
                list.add(new TimeTableFormat(9, 48, 1, 1));
                list.add(new TimeTableFormat(10, 48, 1, 1));
                list.add(new TimeTableFormat(11, 49, 1, 1));
                list.add(new TimeTableFormat(12, 42, 1, 1));
                list.add(new TimeTableFormat(13, 48, 1, 1));
                list.add(new TimeTableFormat(14, 39, 1, 1));
                list.add(new TimeTableFormat(15, 22, 1, 1));
                list.add(new TimeTableFormat(15, 35, 1, 1));
                list.add(new TimeTableFormat(16, 28, 1, 1));
                list.add(new TimeTableFormat(17, 2, 1, 1));
                list.add(new TimeTableFormat(17, 29, 1, 1));
                list.add(new TimeTableFormat(18, 12, 1, 1));
                list.add(new TimeTableFormat(19, 41, 1, 1));
                list.add(new TimeTableFormat(20, 51, 1, 1));
                list.add(new TimeTableFormat(22, 8, 1, 1));
                break;
            case 2:
                //日曜
                list.add(new TimeTableFormat(7, 46, 1, 2));
                list.add(new TimeTableFormat(9, 48, 1, 2));
                list.add(new TimeTableFormat(10, 27, 1, 2));
                list.add(new TimeTableFormat(11, 37, 1, 2));
                list.add(new TimeTableFormat(12, 29, 1, 2));
                list.add(new TimeTableFormat(13, 37, 1, 2));
                list.add(new TimeTableFormat(14, 46, 1, 2));
                list.add(new TimeTableFormat(15, 51, 1, 2));
                list.add(new TimeTableFormat(17, 2, 1, 2));
                list.add(new TimeTableFormat(17, 49, 1, 2));
                list.add(new TimeTableFormat(18, 13, 1, 2));
                list.add(new TimeTableFormat(19, 31, 1, 2));
                list.add(new TimeTableFormat(20, 40, 1, 2));
                list.add(new TimeTableFormat(22, 2, 1, 2));
                break;
            case 3:
                //祝日登校
                list.add(new TimeTableFormat(7, 46, 1, 3));
                list.add(new TimeTableFormat(9, 48, 1, 3));
                list.add(new TimeTableFormat(10, 27, 1, 3));
                list.add(new TimeTableFormat(11, 37, 1, 3));
                list.add(new TimeTableFormat(12, 0, 1, 3));
                list.add(new TimeTableFormat(12, 29, 1, 3));
                list.add(new TimeTableFormat(12, 40, 1, 3));
                list.add(new TimeTableFormat(13, 37, 1, 3));
                list.add(new TimeTableFormat(14, 46, 1, 3));
                list.add(new TimeTableFormat(15, 51, 1, 3));
                list.add(new TimeTableFormat(16, 20, 1, 3));
                list.add(new TimeTableFormat(17, 2, 1, 3));
                list.add(new TimeTableFormat(17, 49, 1, 3));
                list.add(new TimeTableFormat(17, 55, 1, 3));
                list.add(new TimeTableFormat(18, 13, 1, 3));
                list.add(new TimeTableFormat(19, 31, 1, 3));
                list.add(new TimeTableFormat(19, 55, 1, 3));
                list.add(new TimeTableFormat(20, 40, 1, 3));
                list.add(new TimeTableFormat(21, 30, 1, 3));
                list.add(new TimeTableFormat(22, 2, 1, 3));
                break;
        }
    }
}

