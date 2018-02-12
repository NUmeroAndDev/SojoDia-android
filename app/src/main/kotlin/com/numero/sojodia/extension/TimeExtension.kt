package com.numero.sojodia.extension

import android.support.annotation.VisibleForTesting
import com.numero.sojodia.model.Time

/**
 * FIXME
 * 秒は使用していないので、きちんと計算できていない
 */

fun Time.addition(after: Time) {
    var hour = this.hour + after.hour
    var min = this.min + after.min
    var sec = this.sec + after.sec

    min += sec / 60
    sec %= 60
    hour += min / 60
    min %= 60
    hour %= 24

    this.hour = hour
    this.min =min
    this.sec = sec
}

fun Time.subtraction(after: Time) {
    var hour = this.hour - after.hour
    var min = this.min - after.min
    var sec = this.sec - after.sec

    if (sec < 0) {
        sec += 60
        min -= 1
    }

    if (min < 0) {
        min += 60
        hour -= 1
    }

    if (hour < 0) {
        hour += 24
    }
    this.hour = hour
    this.min = min
    this.min = sec
}

fun Time.countTime() {
    val time = Time()
    var hour = time.hour
    var min = time.min
    var sec = 1

    sec = this.sec - sec
    min = this.min - min
    hour = this.hour - hour

    if (sec < 0) {
        sec += 60
        min -= 1
    }

    if (min < 0) {
        min += 60
        hour -= 1
    }

    if (hour < 0) {
        hour += 24
    }
    this.hour = hour
    this.min = min
    this.sec = sec
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun Time.countTime(currentTime: Time) {
    var hour = currentTime.hour
    var min = currentTime.min
    var sec = 1

    sec = this.sec - sec
    min = this.min - min
    hour = this.hour - hour

    if (sec < 0) {
        sec += 60
        min -= 1
    }

    if (min < 0) {
        min += 60
        hour -= 1
    }

    if (hour < 0) {
        hour += 24
    }
    this.hour = hour
    this.min = min
    this.sec = sec
}

fun Time.isOverTime(after: Time): Boolean {
    var sec = after.sec - this.sec
    var min = after.min - this.min
    var hour = after.hour - this.hour

    if (sec < 0) {
        sec += 60
        min--
    }

    if (min < 0) {
        min += 60
        hour--
    }

    return if (hour == 0 && min == 0 && sec == 0) {
        false
    } else hour >= 0
}
