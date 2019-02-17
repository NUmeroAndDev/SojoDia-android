package com.numero.sojodia.model

data class BusTime(
        val time: Time,
        val week: Week,
        val isNonstop: Boolean,
        val isOnlyOnSchooldays: Boolean
)