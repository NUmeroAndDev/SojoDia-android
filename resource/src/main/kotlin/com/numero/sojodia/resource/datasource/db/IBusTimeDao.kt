package com.numero.sojodia.resource.datasource.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.numero.sojodia.resource.datasource.BusTime
import io.reactivex.Maybe

@Dao
interface IBusTimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(busTime: BusTime)

    @Query("SELECT * FROM bus_time ORDER BY routeId ASC, hour ASC, minute ASC, weekId ASC")
    fun findAll(): Maybe<List<BusTime>>

    @Query("DELETE FROM bus_time")
    fun clearTable()
}