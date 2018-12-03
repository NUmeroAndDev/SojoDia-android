package com.numero.sojodia.resource.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface IBusTimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(busTime: BusTime)

    @Query("SELECT * FROM bus_time")
    fun findAll(): Maybe<List<BusTime>>

    @Query("SELECT * FROM bus_time WHERE routeId = :routeId ORDER BY routeId ASC")
    fun find(routeId: String): Flowable<List<BusTime>>
}