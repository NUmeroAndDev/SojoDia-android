package com.numero.sojodia.model

import java.util.*

enum class Week(val id: Int) {

    WEEKDAY(0),
    SATURDAY(1),
    SUNDAY(2);

    companion object {
        fun getCurrentWeek(): Week = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> SUNDAY
            Calendar.SATURDAY -> SATURDAY
            else -> WEEKDAY
        }

        fun getWeek(id: Int): Week? = Week.values().find { week -> week.id == id }
    }
}
