package com.numero.sojodia.extension

import android.support.annotation.VisibleForTesting
import com.numero.sojodia.model.Time

fun Time.createCountTime(): Time {
    val time = Time()
    var hour = this.hour - time.hour
    var min = this.min - time.min

    if (min < 0) {
        min += 60
        hour -= 1
    }

    if (hour < 0) {
        hour += 24
    }
    return Time(hour, min)
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun Time.createCountTime(currentTime: Time): Time {
    var hour = this.hour - currentTime.hour
    var min = this.min - currentTime.min

    if (min < 0) {
        min += 60
        hour -= 1
    }

    if (hour < 0) {
        hour += 24
    }
    return Time(hour, min)
}

fun Time.isOverTime(after: Time): Boolean {
    var min = after.min - this.min
    var hour = after.hour - this.hour

    if (min < 0) {
        min += 60
        hour--
    }

    return if (hour == 0 && min == 0) {
        false
    } else hour >= 0
}
