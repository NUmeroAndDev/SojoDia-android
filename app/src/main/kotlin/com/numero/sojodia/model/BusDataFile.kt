package com.numero.sojodia.model

enum class BusDataFile(val fileName: String) {

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

    val url: String = "https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/$fileName"

}
