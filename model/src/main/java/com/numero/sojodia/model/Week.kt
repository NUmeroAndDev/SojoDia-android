package com.numero.sojodia.model

import java.util.*

enum class Week {

    WEEKDAY, SATURDAY, SUNDAY;

    companion object {
        fun getCurrentWeek(): Week = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> SUNDAY
            Calendar.SATURDAY -> SATURDAY
            else -> WEEKDAY
        }

        fun from(weekId: Int): Week {
            return when (weekId) {
                0 -> WEEKDAY
                1 -> SATURDAY
                2 -> SUNDAY
                else -> throw IllegalArgumentException()
            }
        }
    }
}
