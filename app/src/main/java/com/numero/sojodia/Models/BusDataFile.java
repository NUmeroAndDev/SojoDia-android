package com.numero.sojodia.Models;

public class BusDataFile {

    public String URL, name;

    public BusDataFile(String URL, String name){
        this.URL = URL;
        this.name = name;
    }

    public static BusDataFile[] init(){
        BusDataFile files[] = new BusDataFile[4];

        files[0] = new BusDataFile("https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/TkToKutc.csv", "TkToKutc.csv");
        files[1] = new BusDataFile("https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/TndToKutc.csv", "TndToKutc.csv");
        files[2] = new BusDataFile("https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/KutcToTk.csv", "KutcToTk.csv");
        files[3] = new BusDataFile("https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/KutcToTnd.csv", "KutcToTnd.csv");

        return files;
    }

}
