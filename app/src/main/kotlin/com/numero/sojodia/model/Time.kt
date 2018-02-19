package com.numero.sojodia.model

import java.util.*

class Time {

    var hour: Int = 0
    var min: Int = 0

    constructor() {
        Calendar.getInstance().let {
            hour = it.get(Calendar.HOUR_OF_DAY)
            min = it.get(Calendar.MINUTE)
        }
    }

    constructor(hour: Int, min: Int) {
        this.hour = hour
        this.min = min
    }

    fun setTime(hour: Int, min: Int) {
        this.hour = hour
        this.min = min
    }
}
