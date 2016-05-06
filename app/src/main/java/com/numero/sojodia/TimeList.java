package com.numero.sojodia;

import java.util.ArrayList;

public class TimeList {

    ArrayList<TimeFormat> list;

    TimeList() {
    }

    public void setTK(ArrayList<TimeFormat> list, int week, int round){
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

    public void setTND(ArrayList<TimeFormat> list, int week, int round){
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

    public void setAll(ArrayList<TimeFormat> list, int route, int round){
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
                list.add(new TimeFormat(6, 35, 0, 0));
                list.add(new TimeFormat(7, 23, 0, 0));
                list.add(new TimeFormat(7, 55, 0, 0));
                list.add(new TimeFormat(8, 8, 0, 0));
                list.add(new TimeFormat(8, 13, 0, 0));
                list.add(new TimeFormat(8, 18, 0, 0));
                list.add(new TimeFormat(8, 22, 0, 0));
                list.add(new TimeFormat(8, 26, 0, 0));
                list.add(new TimeFormat(8, 29, 0, 0));
                list.add(new TimeFormat(8, 32, 0, 0));
                list.add(new TimeFormat(8, 35, 0, 0));
                list.add(new TimeFormat(8, 40, 0, 0));
                list.add(new TimeFormat(8, 45, 0, 0));
                list.add(new TimeFormat(8, 55, 0, 0));
                list.add(new TimeFormat(9, 15, 0, 0));
                list.add(new TimeFormat(9, 25, 0, 0));
                list.add(new TimeFormat(9, 35, 0, 0));
                list.add(new TimeFormat(9, 47, 0, 0));
                list.add(new TimeFormat(9, 55, 0, 0));
                list.add(new TimeFormat(10, 0, 0, 0));
                list.add(new TimeFormat(10, 5, 0, 0));
                list.add(new TimeFormat(10, 10, 0, 0));
                list.add(new TimeFormat(10, 15, 0, 0));
                list.add(new TimeFormat(10, 25, 0, 0));
                list.add(new TimeFormat(10, 35, 0, 0));
                list.add(new TimeFormat(10, 43, 0, 0));
                list.add(new TimeFormat(10, 55, 0, 0));
                list.add(new TimeFormat(11, 15, 0, 0));
                list.add(new TimeFormat(11, 35, 0, 0));
                list.add(new TimeFormat(11, 50, 0, 0));
                list.add(new TimeFormat(12, 5, 0, 0));
                list.add(new TimeFormat(12, 20, 0, 0));
                list.add(new TimeFormat(12, 30, 0, 0));
                list.add(new TimeFormat(12, 35, 0, 0));
                list.add(new TimeFormat(12, 50, 0, 0));
                list.add(new TimeFormat(13, 10, 0, 0));
                list.add(new TimeFormat(13, 35, 0, 0));
                list.add(new TimeFormat(13, 50, 0, 0));
                list.add(new TimeFormat(14, 5, 0, 0));
                list.add(new TimeFormat(14, 20, 0, 0));
                list.add(new TimeFormat(14, 40, 0, 0));
                list.add(new TimeFormat(15, 5, 0, 0));
                list.add(new TimeFormat(15, 20, 0, 0));
                list.add(new TimeFormat(15, 35, 0, 0));
                list.add(new TimeFormat(15, 50, 0, 0));
                list.add(new TimeFormat(16, 5, 0, 0));
                list.add(new TimeFormat(16, 20, 0, 0));
                list.add(new TimeFormat(16, 35, 0, 0));
                list.add(new TimeFormat(16, 50, 0, 0));
                list.add(new TimeFormat(17, 5, 0, 0));
                list.add(new TimeFormat(17, 20, 0, 0));
                list.add(new TimeFormat(17, 35, 0, 0));
                list.add(new TimeFormat(17, 50, 0, 0));
                list.add(new TimeFormat(18, 5, 0, 0));
                list.add(new TimeFormat(18, 20, 0, 0));
                list.add(new TimeFormat(18, 35, 0, 0));
                list.add(new TimeFormat(18, 50, 0, 0));
                list.add(new TimeFormat(19, 5, 0, 0));
                list.add(new TimeFormat(19, 20, 0, 0));
                list.add(new TimeFormat(19, 35, 0, 0));
                list.add(new TimeFormat(19, 50, 0, 0));
                list.add(new TimeFormat(20, 10, 0, 0));
                list.add(new TimeFormat(20, 30, 0, 0));
                list.add(new TimeFormat(20, 50, 0, 0));
                list.add(new TimeFormat(21, 20, 0, 0));
                list.add(new TimeFormat(21, 50, 0, 0));
                list.add(new TimeFormat(23, 1, 0, 0));
                break;
            case 1:
                //土曜
                list.add(new TimeFormat(8, 0, 0, 1));
                list.add(new TimeFormat(8, 14, 0, 1));
                list.add(new TimeFormat(8, 28, 0, 1));
                list.add(new TimeFormat(8, 44, 0, 1));
                list.add(new TimeFormat(9, 2, 0, 1));
                list.add(new TimeFormat(9, 20, 0, 1));
                list.add(new TimeFormat(9, 38, 0, 1));
                list.add(new TimeFormat(9, 55, 0, 1));
                list.add(new TimeFormat(10, 10, 0, 1));
                list.add(new TimeFormat(10, 30, 0, 1));
                list.add(new TimeFormat(10, 50, 0, 1));
                list.add(new TimeFormat(11, 10, 0, 1));
                list.add(new TimeFormat(11, 30, 0, 1));
                list.add(new TimeFormat(11, 50, 0, 1));
                list.add(new TimeFormat(12, 10, 0, 1));
                list.add(new TimeFormat(12, 30, 0, 1));
                list.add(new TimeFormat(12, 50, 0, 1));
                list.add(new TimeFormat(13, 10, 0, 1));
                list.add(new TimeFormat(13, 30, 0, 1));
                list.add(new TimeFormat(13, 50, 0, 1));
                list.add(new TimeFormat(14, 10, 0, 1));
                list.add(new TimeFormat(14, 30, 0, 1));
                list.add(new TimeFormat(14, 50, 0, 1));
                list.add(new TimeFormat(15, 10, 0, 1));
                list.add(new TimeFormat(15, 30, 0, 1));
                list.add(new TimeFormat(15, 50, 0, 1));
                list.add(new TimeFormat(16, 10, 0, 1));
                list.add(new TimeFormat(16, 30, 0, 1));
                list.add(new TimeFormat(16, 50, 0, 1));
                list.add(new TimeFormat(17, 10, 0, 1));
                list.add(new TimeFormat(17, 30, 0, 1));
                list.add(new TimeFormat(17, 50, 0, 1));
                list.add(new TimeFormat(21, 0, 0, 1));
                break;
            case 2:
                //日祝
                list.add(new TimeFormat(8, 5, 0, 2));
                list.add(new TimeFormat(8, 33, 0, 2));
                list.add(new TimeFormat(8, 50, 0, 2));
                list.add(new TimeFormat(9, 10, 0, 2));
                list.add(new TimeFormat(9, 40, 0, 2));
                list.add(new TimeFormat(10, 10, 0, 2));
                list.add(new TimeFormat(10, 40, 0, 2));
                list.add(new TimeFormat(11, 10, 0, 2));
                list.add(new TimeFormat(11, 40, 0, 2));
                list.add(new TimeFormat(12, 10, 0, 2));
                list.add(new TimeFormat(12, 40, 0, 2));
                list.add(new TimeFormat(13, 10, 0, 2));
                list.add(new TimeFormat(13, 40, 0, 2));
                list.add(new TimeFormat(14, 10, 0, 2));
                list.add(new TimeFormat(14, 40, 0, 2));
                list.add(new TimeFormat(15, 10, 0, 2));
                list.add(new TimeFormat(15, 40, 0, 2));
                list.add(new TimeFormat(16, 10, 0, 2));
                list.add(new TimeFormat(16, 40, 0, 2));
                list.add(new TimeFormat(17, 10, 0, 2));
                break;
            case 3:
                //祝日登校
                list.add(new TimeFormat(8, 5, 0, 3));
                list.add(new TimeFormat(8, 10, 0, 3));
                list.add(new TimeFormat(8, 15, 0, 3));
                list.add(new TimeFormat(8, 20, 0, 3));
                list.add(new TimeFormat(8, 25, 0, 3));
                list.add(new TimeFormat(8, 30, 0, 3));
                list.add(new TimeFormat(8, 33, 0, 3));
                list.add(new TimeFormat(8, 36, 0, 3));
                list.add(new TimeFormat(8, 40, 0, 3));
                list.add(new TimeFormat(8, 50, 0, 3));
                list.add(new TimeFormat(8, 55, 0, 3));
                list.add(new TimeFormat(9, 10, 0, 3));
                list.add(new TimeFormat(9, 20, 0, 3));
                list.add(new TimeFormat(9, 30, 0, 3));
                list.add(new TimeFormat(9, 40, 0, 3));
                list.add(new TimeFormat(9, 50, 0, 3));
                list.add(new TimeFormat(9, 55, 0, 3));
                list.add(new TimeFormat(10, 0, 0, 3));
                list.add(new TimeFormat(10, 10, 0, 3));
                list.add(new TimeFormat(10, 20, 0, 3));
                list.add(new TimeFormat(10, 40, 0, 3));
                list.add(new TimeFormat(11, 10, 0, 3));
                list.add(new TimeFormat(11, 40, 0, 3));
                list.add(new TimeFormat(12, 0, 0, 3));
                list.add(new TimeFormat(12, 10, 0, 3));
                list.add(new TimeFormat(12, 20, 0, 3));
                list.add(new TimeFormat(12, 30, 0, 3));
                list.add(new TimeFormat(12, 40, 0, 3));
                list.add(new TimeFormat(13, 10, 0, 3));
                list.add(new TimeFormat(13, 40, 0, 3));
                list.add(new TimeFormat(14, 10, 0, 3));
                list.add(new TimeFormat(14, 40, 0, 3));
                list.add(new TimeFormat(15, 10, 0, 3));
                list.add(new TimeFormat(15, 40, 0, 3));
                list.add(new TimeFormat(16, 10, 0, 3));
                list.add(new TimeFormat(16, 40, 0, 3));
                list.add(new TimeFormat(17, 10, 0, 3));
                list.add(new TimeFormat(19, 10, 0, 3));
                break;
        }
    }

    //関大→高槻
    private void setDepartureTK(int week){
        switch (week){
            case 0:
                //平日
                list.add(new TimeFormat(7, 4, 1, 0));
                list.add(new TimeFormat(7, 33, 1, 0));
                list.add(new TimeFormat(8, 3, 1, 0));
                list.add(new TimeFormat(8, 48, 1, 0));
                list.add(new TimeFormat(9, 13, 1, 0));
                list.add(new TimeFormat(9, 28, 1, 0));
                list.add(new TimeFormat(10, 4, 1, 0));
                list.add(new TimeFormat(10, 23, 1, 0));
                list.add(new TimeFormat(10, 42, 1, 0));
                list.add(new TimeFormat(11, 3, 1, 0));
                list.add(new TimeFormat(11, 19, 1, 0));
                list.add(new TimeFormat(11, 35, 1, 0));
                list.add(new TimeFormat(11, 51, 1, 0));
                list.add(new TimeFormat(12, 10, 1, 0));
                list.add(new TimeFormat(12, 20, 1, 0));
                list.add(new TimeFormat(12, 30, 1, 0));
                list.add(new TimeFormat(12, 40, 1, 0));
                list.add(new TimeFormat(12, 50, 1, 0));
                list.add(new TimeFormat(13, 4, 1, 0));
                list.add(new TimeFormat(13, 24, 1, 0));
                list.add(new TimeFormat(13, 44, 1, 0));
                list.add(new TimeFormat(14, 4, 1, 0));
                list.add(new TimeFormat(14, 24, 1, 0));
                list.add(new TimeFormat(14, 37, 1, 0));
                list.add(new TimeFormat(14, 41, 1, 0));
                list.add(new TimeFormat(14, 45, 1, 0));
                list.add(new TimeFormat(14, 50, 1, 0));
                list.add(new TimeFormat(15, 9, 1, 0));
                list.add(new TimeFormat(15, 36, 1, 0));
                list.add(new TimeFormat(15, 51, 1, 0));
                list.add(new TimeFormat(16, 10, 1, 0));
                list.add(new TimeFormat(16, 15, 1, 0));
                list.add(new TimeFormat(16, 20, 1, 0));
                list.add(new TimeFormat(16, 24, 1, 0));
                list.add(new TimeFormat(16, 28, 1, 0));
                list.add(new TimeFormat(16, 36, 1, 0));
                list.add(new TimeFormat(16, 51, 1, 0));
                list.add(new TimeFormat(17, 5, 1, 0));
                list.add(new TimeFormat(17, 15, 1, 0));
                list.add(new TimeFormat(17, 25, 1, 0));
                list.add(new TimeFormat(17, 43, 1, 0));
                list.add(new TimeFormat(17, 55, 1, 0));
                list.add(new TimeFormat(17, 59, 1, 0));
                list.add(new TimeFormat(18, 3, 1, 0));
                list.add(new TimeFormat(18, 8, 1, 0));
                list.add(new TimeFormat(18, 14, 1, 0));
                list.add(new TimeFormat(18, 34, 1, 0));
                list.add(new TimeFormat(18, 50, 1, 0));
                list.add(new TimeFormat(19, 10, 1, 0));
                list.add(new TimeFormat(19, 40, 1, 0));
                list.add(new TimeFormat(19, 55, 1, 0));
                list.add(new TimeFormat(20, 10, 1, 0));
                list.add(new TimeFormat(20, 35, 1, 0));
                list.add(new TimeFormat(21, 20, 1, 0));
                list.add(new TimeFormat(21, 49, 1, 0));
                list.add(new TimeFormat(22, 19, 1, 0));
                break;
            case 1:
                //土曜
                list.add(new TimeFormat(7, 49, 1, 1));
                list.add(new TimeFormat(8, 19, 1, 1));
                list.add(new TimeFormat(8, 39, 1, 1));
                list.add(new TimeFormat(8, 58, 1, 1));
                list.add(new TimeFormat(9, 17, 1, 1));
                list.add(new TimeFormat(9, 37, 1, 1));
                list.add(new TimeFormat(9, 57, 1, 1));
                list.add(new TimeFormat(10, 17, 1, 1));
                list.add(new TimeFormat(10, 37, 1, 1));
                list.add(new TimeFormat(10, 57, 1, 1));
                list.add(new TimeFormat(11, 17, 1, 1));
                list.add(new TimeFormat(11, 37, 1, 1));
                list.add(new TimeFormat(11, 57, 1, 1));
                list.add(new TimeFormat(12, 17, 1, 1));
                list.add(new TimeFormat(12, 37, 1, 1));
                list.add(new TimeFormat(12, 57, 1, 1));
                list.add(new TimeFormat(13, 17, 1, 1));
                list.add(new TimeFormat(13, 37, 1, 1));
                list.add(new TimeFormat(13, 57, 1, 1));
                list.add(new TimeFormat(14, 17, 1, 1));
                list.add(new TimeFormat(14, 37, 1, 1));
                list.add(new TimeFormat(14, 57, 1, 1));
                list.add(new TimeFormat(15, 17, 1, 1));
                list.add(new TimeFormat(15, 37, 1, 1));
                list.add(new TimeFormat(15, 57, 1, 1));
                list.add(new TimeFormat(16, 17, 1, 1));
                list.add(new TimeFormat(16, 37, 1, 1));
                list.add(new TimeFormat(16, 57, 1, 1));
                list.add(new TimeFormat(17, 17, 1, 1));
                list.add(new TimeFormat(17, 37, 1, 1));
                list.add(new TimeFormat(17, 57, 1, 1));
                list.add(new TimeFormat(19, 44, 1, 1));
                list.add(new TimeFormat(21, 30, 1, 1));
                break;
            case 2:
                //日曜
                list.add(new TimeFormat(7, 59, 1, 2));
                list.add(new TimeFormat(8, 41, 1, 2));
                list.add(new TimeFormat(9, 9, 1, 2));
                list.add(new TimeFormat(9, 41, 1, 2));
                list.add(new TimeFormat(10, 11, 1, 2));
                list.add(new TimeFormat(10, 41, 1, 2));
                list.add(new TimeFormat(11, 11, 1, 2));
                list.add(new TimeFormat(11, 41, 1, 2));
                list.add(new TimeFormat(12, 11, 1, 2));
                list.add(new TimeFormat(12, 41, 1, 2));
                list.add(new TimeFormat(13, 11, 1, 2));
                list.add(new TimeFormat(13, 41, 1, 2));
                list.add(new TimeFormat(14, 11, 1, 2));
                list.add(new TimeFormat(14, 41, 1, 2));
                list.add(new TimeFormat(15, 11, 1, 2));
                list.add(new TimeFormat(15, 41, 1, 2));
                list.add(new TimeFormat(16, 11, 1, 2));
                list.add(new TimeFormat(16, 41, 1, 2));
                list.add(new TimeFormat(17, 11, 1, 2));
                break;
            case 3:
                //祝日登校
                list.add(new TimeFormat(7, 59, 1, 3));
                list.add(new TimeFormat(8, 41, 1, 3));
                list.add(new TimeFormat(9, 9, 1, 3));
                list.add(new TimeFormat(9, 41, 1, 3));
                list.add(new TimeFormat(10, 11, 1, 3));
                list.add(new TimeFormat(10, 41, 1, 3));
                list.add(new TimeFormat(11, 11, 1, 3));
                list.add(new TimeFormat(11, 41, 1, 3));
                list.add(new TimeFormat(11, 50, 1, 3));
                list.add(new TimeFormat(12, 11, 1, 3));
                list.add(new TimeFormat(12, 20, 1, 3));
                list.add(new TimeFormat(12, 30, 1, 3));
                list.add(new TimeFormat(12, 41, 1, 3));
                list.add(new TimeFormat(13, 11, 1, 3));
                list.add(new TimeFormat(13, 41, 1, 3));
                list.add(new TimeFormat(14, 11, 1, 3));
                list.add(new TimeFormat(14, 41, 1, 3));
                list.add(new TimeFormat(14, 45, 1, 3));
                list.add(new TimeFormat(14, 50, 1, 3));
                list.add(new TimeFormat(15, 11, 1, 3));
                list.add(new TimeFormat(15, 41, 1, 3));
                list.add(new TimeFormat(16, 11, 1, 3));
                list.add(new TimeFormat(16, 15, 1, 3));
                list.add(new TimeFormat(16, 25, 1, 3));
                list.add(new TimeFormat(16, 30, 1, 3));
                list.add(new TimeFormat(16, 41, 1, 3));
                list.add(new TimeFormat(17, 11, 1, 3));
                list.add(new TimeFormat(17, 20, 1, 3));
                list.add(new TimeFormat(17, 30, 1, 3));
                list.add(new TimeFormat(17, 40, 1, 3));
                list.add(new TimeFormat(17, 50, 1, 3));
                list.add(new TimeFormat(18, 0, 1, 3));
                list.add(new TimeFormat(18, 10, 1, 3));
                list.add(new TimeFormat(18, 20, 1, 3));
                list.add(new TimeFormat(18, 30, 1, 3));
                list.add(new TimeFormat(20, 0, 1, 3));
                break;
        }
    }

    //富田→関大
    private void setArrivalTND(int week) {
        switch (week) {
            case 0:
                //平日
                list.add(new TimeFormat(6, 20, 0, 0));
                list.add(new TimeFormat(7, 3, 0, 0));
                list.add(new TimeFormat(7, 29, 0, 0));
                list.add(new TimeFormat(8, 20, 0, 0));
                list.add(new TimeFormat(8, 25, 0, 0));
                list.add(new TimeFormat(8, 31, 0, 0));
                list.add(new TimeFormat(8, 34, 0, 0));
                list.add(new TimeFormat(8, 38, 0, 0));
                list.add(new TimeFormat(9, 35, 0, 0));
                list.add(new TimeFormat(9, 55, 0, 0));
                list.add(new TimeFormat(10, 10, 0, 0));
                list.add(new TimeFormat(10, 22, 0, 0));
                list.add(new TimeFormat(11, 10, 0, 0));
                list.add(new TimeFormat(11, 56, 0, 0));
                list.add(new TimeFormat(12, 30, 0, 0));
                list.add(new TimeFormat(13, 2, 0, 0));
                list.add(new TimeFormat(13, 22, 0, 0));
                list.add(new TimeFormat(13, 58, 0, 0));
                list.add(new TimeFormat(14, 15, 0, 0));
                list.add(new TimeFormat(14, 45, 0, 0));
                list.add(new TimeFormat(15, 10, 0, 0));
                list.add(new TimeFormat(15, 58, 0, 0));
                list.add(new TimeFormat(16, 30, 0, 0));
                list.add(new TimeFormat(16, 45, 0, 0));
                list.add(new TimeFormat(16, 59, 0, 0));
                list.add(new TimeFormat(17, 18, 0, 0));
                list.add(new TimeFormat(17, 38, 0, 0));
                list.add(new TimeFormat(18, 5, 0, 0));
                list.add(new TimeFormat(18, 40, 0, 0));
                list.add(new TimeFormat(19, 5, 0, 0));
                list.add(new TimeFormat(19, 35, 0, 0));
                list.add(new TimeFormat(20, 45, 0, 0));
                list.add(new TimeFormat(21, 50, 0, 0));
                break;
            case 1:
                //土曜
                list.add(new TimeFormat(6, 23, 0, 1));
                list.add(new TimeFormat(7, 5, 0, 1));
                list.add(new TimeFormat(7, 29, 0, 1));
                list.add(new TimeFormat(8, 30, 0, 1));
                list.add(new TimeFormat(9, 5, 0, 1));
                list.add(new TimeFormat(10, 5, 0, 1));
                list.add(new TimeFormat(11, 12, 0, 1));
                list.add(new TimeFormat(12, 1, 0, 1));
                list.add(new TimeFormat(12, 25, 0, 1));
                list.add(new TimeFormat(13, 7, 0, 1));
                list.add(new TimeFormat(14, 5, 0, 1));
                list.add(new TimeFormat(14, 39, 0, 1));
                list.add(new TimeFormat(15, 5, 0, 1));
                list.add(new TimeFormat(15, 47, 0, 1));
                list.add(new TimeFormat(16, 21, 0, 1));
                list.add(new TimeFormat(17, 30, 0, 1));
                list.add(new TimeFormat(18, 55, 0, 1));
                list.add(new TimeFormat(20, 10, 0, 1));
                list.add(new TimeFormat(21, 27, 0, 1));
                break;
            case 2:
                //日曜
                list.add(new TimeFormat(7, 5, 0, 2));
                list.add(new TimeFormat(8, 10, 0, 2));
                list.add(new TimeFormat(9, 5, 0, 2));
                list.add(new TimeFormat(9, 55, 0, 2));
                list.add(new TimeFormat(10, 55, 0, 2));
                list.add(new TimeFormat(11, 48, 0, 2));
                list.add(new TimeFormat(12, 55, 0, 2));
                list.add(new TimeFormat(13, 30, 0, 2));
                list.add(new TimeFormat(14, 5, 0, 2));
                list.add(new TimeFormat(15, 10, 0, 2));
                list.add(new TimeFormat(16, 21, 0, 2));
                list.add(new TimeFormat(17, 30, 0, 2));
                list.add(new TimeFormat(18, 50, 0, 2));
                list.add(new TimeFormat(19, 59, 0, 2));
                list.add(new TimeFormat(21, 21, 0, 2));
                break;
            case 3:
                //祝日登校
                list.add(new TimeFormat(7, 5, 0, 3));
                list.add(new TimeFormat(8, 10, 0, 3));
                list.add(new TimeFormat(8, 20, 0, 3));
                list.add(new TimeFormat(8, 30, 0, 3));
                list.add(new TimeFormat(9, 5, 0, 3));
                list.add(new TimeFormat(9, 30, 0, 3));
                list.add(new TimeFormat(9, 55, 0, 3));
                list.add(new TimeFormat(10, 10, 0, 3));
                list.add(new TimeFormat(10, 15, 0, 3));
                list.add(new TimeFormat(10, 55, 0, 3));
                list.add(new TimeFormat(11, 48, 0, 3));
                list.add(new TimeFormat(12, 30, 0, 3));
                list.add(new TimeFormat(12, 55, 0, 3));
                list.add(new TimeFormat(13, 30, 0, 3));
                list.add(new TimeFormat(14, 0, 0, 3));
                list.add(new TimeFormat(15, 10, 0, 3));
                list.add(new TimeFormat(16, 21, 0, 3));
                list.add(new TimeFormat(17, 30, 0, 3));
                list.add(new TimeFormat(18, 50, 0, 3));
                list.add(new TimeFormat(19, 59, 0, 3));
                list.add(new TimeFormat(21, 21, 0, 3));
                break;
        }
    }

    //関大→富田
    private void setDepartureTND(int week) {
        switch (week) {
            case 0:
                //平日
                list.add(new TimeFormat(6, 35, 1, 0));
                list.add(new TimeFormat(7, 1, 1, 0));
                list.add(new TimeFormat(7, 44, 1, 0));
                list.add(new TimeFormat(8, 10, 1, 0));
                list.add(new TimeFormat(9, 5, 1, 0));
                list.add(new TimeFormat(9, 18, 1, 0));
                list.add(new TimeFormat(9, 30, 1, 0));
                list.add(new TimeFormat(9, 45, 1, 0));
                list.add(new TimeFormat(10, 40, 1, 0));
                list.add(new TimeFormat(11, 3, 1, 0));
                list.add(new TimeFormat(11, 40, 1, 0));
                list.add(new TimeFormat(12, 39, 1, 0));
                list.add(new TimeFormat(13, 7, 1, 0));
                list.add(new TimeFormat(13, 43, 1, 0));
                list.add(new TimeFormat(14, 41, 1, 0));
                list.add(new TimeFormat(15, 20, 1, 0));
                list.add(new TimeFormat(16, 25, 1, 0));
                list.add(new TimeFormat(16, 31, 1, 0));
                list.add(new TimeFormat(16, 49, 1, 0));
                list.add(new TimeFormat(17, 10, 1, 0));
                list.add(new TimeFormat(17, 38, 1, 0));
                list.add(new TimeFormat(18, 1, 1, 0));
                list.add(new TimeFormat(18, 10, 1, 0));
                list.add(new TimeFormat(18, 30, 1, 0));
                list.add(new TimeFormat(19, 36, 1, 0));
                list.add(new TimeFormat(19, 45, 1, 0));
                list.add(new TimeFormat(20, 16, 1, 0));
                list.add(new TimeFormat(20, 45, 1, 0));
                list.add(new TimeFormat(21, 30, 1, 0));
                list.add(new TimeFormat(22, 31, 1, 0));
                break;
            case 1:
                //土曜
                list.add(new TimeFormat(7, 4, 1, 1));
                list.add(new TimeFormat(7, 46, 1, 1));
                list.add(new TimeFormat(8, 10, 1, 1));
                list.add(new TimeFormat(9, 14, 1, 1));
                list.add(new TimeFormat(9, 48, 1, 1));
                list.add(new TimeFormat(10, 48, 1, 1));
                list.add(new TimeFormat(11, 49, 1, 1));
                list.add(new TimeFormat(12, 42, 1, 1));
                list.add(new TimeFormat(13, 48, 1, 1));
                list.add(new TimeFormat(14, 39, 1, 1));
                list.add(new TimeFormat(15, 22, 1, 1));
                list.add(new TimeFormat(15, 35, 1, 1));
                list.add(new TimeFormat(16, 28, 1, 1));
                list.add(new TimeFormat(17, 2, 1, 1));
                list.add(new TimeFormat(17, 29, 1, 1));
                list.add(new TimeFormat(18, 12, 1, 1));
                list.add(new TimeFormat(19, 41, 1, 1));
                list.add(new TimeFormat(20, 51, 1, 1));
                list.add(new TimeFormat(22, 8, 1, 1));
                break;
            case 2:
                //日曜
                list.add(new TimeFormat(7, 46, 1, 2));
                list.add(new TimeFormat(9, 48, 1, 2));
                list.add(new TimeFormat(10, 27, 1, 2));
                list.add(new TimeFormat(11, 37, 1, 2));
                list.add(new TimeFormat(12, 29, 1, 2));
                list.add(new TimeFormat(13, 37, 1, 2));
                list.add(new TimeFormat(14, 46, 1, 2));
                list.add(new TimeFormat(15, 51, 1, 2));
                list.add(new TimeFormat(17, 2, 1, 2));
                list.add(new TimeFormat(17, 49, 1, 2));
                list.add(new TimeFormat(18, 13, 1, 2));
                list.add(new TimeFormat(19, 31, 1, 2));
                list.add(new TimeFormat(20, 40, 1, 2));
                list.add(new TimeFormat(22, 2, 1, 2));
                break;
            case 3:
                //祝日登校
                list.add(new TimeFormat(7, 46, 1, 3));
                list.add(new TimeFormat(9, 48, 1, 3));
                list.add(new TimeFormat(10, 27, 1, 3));
                list.add(new TimeFormat(11, 37, 1, 3));
                list.add(new TimeFormat(12, 0, 1, 3));
                list.add(new TimeFormat(12, 29, 1, 3));
                list.add(new TimeFormat(12, 40, 1, 3));
                list.add(new TimeFormat(13, 37, 1, 3));
                list.add(new TimeFormat(14, 46, 1, 3));
                list.add(new TimeFormat(15, 51, 1, 3));
                list.add(new TimeFormat(16, 20, 1, 3));
                list.add(new TimeFormat(17, 2, 1, 3));
                list.add(new TimeFormat(17, 49, 1, 3));
                list.add(new TimeFormat(17, 55, 1, 3));
                list.add(new TimeFormat(18, 13, 1, 3));
                list.add(new TimeFormat(19, 31, 1, 3));
                list.add(new TimeFormat(19, 55, 1, 3));
                list.add(new TimeFormat(20, 40, 1, 3));
                list.add(new TimeFormat(21, 30, 1, 3));
                list.add(new TimeFormat(22, 2, 1, 3));
                break;
        }
    }
}

