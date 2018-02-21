package com.numero.sojodia.extension

import android.support.annotation.VisibleForTesting
import com.numero.sojodia.model.Time

/**
 * @return 現在時刻からのカウント
 */
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

/**
 * @param time 比較用のTime
 *
 * @return this > time -> true
 */
fun Time.isOverTime(time: Time): Boolean {
    var min = this.min - time.min
    var hour = this.hour - time.hour

    if (min < 0) {
        min += 60
        hour--
    }

    return if (hour == 0 && min == 0) {
        false
    } else hour >= 0
}
