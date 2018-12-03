package com.numero.sojodia.resource.datasource

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(BusTime::class)], version = 1)
abstract class BusTimeDatabase : RoomDatabase() {

    abstract fun busTimeDao(): IBusTimeDao

}