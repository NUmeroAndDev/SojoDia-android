package com.numero.sojodia.resource.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(BusTimeData::class)], version = 1)
abstract class BusTimeDatabase : RoomDatabase() {

    abstract fun busTimeDao(): IBusTimeDao

}