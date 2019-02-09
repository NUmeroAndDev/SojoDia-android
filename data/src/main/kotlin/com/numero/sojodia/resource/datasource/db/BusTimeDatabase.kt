package com.numero.sojodia.resource.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.numero.sojodia.resource.datasource.BusTime

@Database(entities = [(BusTime::class)], version = 1)
abstract class BusTimeDatabase : RoomDatabase() {

    abstract fun busTimeDao(): IBusTimeDao

}