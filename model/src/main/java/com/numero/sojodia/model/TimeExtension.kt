package com.numero.sojodia.model

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
