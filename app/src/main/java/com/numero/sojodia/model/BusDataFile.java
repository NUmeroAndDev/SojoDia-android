package com.numero.sojodia.model;

public enum BusDataFile {

    TK_TO_KUTC(
            "TkToKutc.csv"
    ),

    TND_TO_KUTC(
            "TndToKutc.csv"
    ),

    KUTC_TO_TK(
            "KutcToTk.csv"
    ),

    KUTC_TO_TND(
            "KutcToTnd.csv"
    );

    private static final String baseUrl = "https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/%s";

    private String url;
    private String fileName;

    BusDataFile(String fileName) {
        this.url = String.format(baseUrl, fileName);
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }
}
