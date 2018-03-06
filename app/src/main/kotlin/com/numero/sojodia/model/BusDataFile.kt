package com.numero.sojodia.model

import com.numero.sojodia.BuildConfig

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

    val url: String = BuildConfig.BUS_FILE_URL.format(fileName)

}
