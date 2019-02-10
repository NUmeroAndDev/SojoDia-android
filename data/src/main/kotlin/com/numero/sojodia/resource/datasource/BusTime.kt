package com.numero.sojodia.resource.datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_time")
data class BusTime(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val routeId: Int,
        val hour: Int,
        val minute: Int,
        val weekId: Int,
        val isNonstop: Boolean,
        val isOnlyOnSchooldays: Boolean)