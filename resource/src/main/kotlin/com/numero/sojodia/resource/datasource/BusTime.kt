package com.numero.sojodia.resource.datasource

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.numero.sojodia.resource.model.Route
import com.numero.sojodia.resource.model.Time
import com.numero.sojodia.resource.model.Week

@Entity(tableName = "bus_time")
data class BusTime(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val routeId: Int,
        val hour: Int,
        val minute: Int,
        val weekId: Int,
        val isNonstop: Boolean,
        val isOnlyOnSchooldays: Boolean) {

    @Ignore
    val route: Route = Route.find(routeId)

    @Ignore
    val week: Week? = Week.find(weekId)

    @Ignore
    val time: Time = Time(hour, minute)
}