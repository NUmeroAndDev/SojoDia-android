package com.numero.sojodia.model

import java.util.*

class Time {

    var hour: Int = 0
    var min: Int = 0
    var sec: Int = 0

    constructor() {
        Calendar.getInstance().let {
            hour = it.get(Calendar.HOUR_OF_DAY)
            min = it.get(Calendar.MINUTE)
            sec = it.get(Calendar.SECOND)
        }
    }

    constructor(hour: Int, min: Int, sec: Int) {
        this.hour = hour
        this.min = min
        this.sec = sec
    }

    fun setTime(hour: Int, min: Int, sec: Int) {
        this.hour = hour
        this.min = min
        this.sec = sec
    }

    override fun toString(): String {
        return String.format(Locale.ENGLISH, "%02d:%02d:%02d", hour, min, sec)
    }
}
