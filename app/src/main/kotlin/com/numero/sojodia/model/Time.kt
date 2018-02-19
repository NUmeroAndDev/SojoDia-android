package com.numero.sojodia.model

import java.util.*

class Time {

    val hour: Int
    val min: Int

    constructor() {
        val calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        min = calendar.get(Calendar.MINUTE)
    }

    constructor(hour: Int, min: Int) {
        this.hour = hour
        this.min = min
    }
}
