package com.numero.sojodia.extension

import android.text.format.DateFormat
import java.util.*

fun Calendar.getTodayStringOnlyFigure(): String {
    return DateFormat.format("yyyyMMdd", this).toString()
}

fun Calendar.getTodayString(pattern: String): String {
    return DateFormat.format(pattern, this).toString()
}

fun Date.format(format: String): String {
    return DateFormat.format(format, this).toString()
}