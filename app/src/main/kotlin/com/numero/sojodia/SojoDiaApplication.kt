package com.numero.sojodia

import android.app.Application
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.BusDataRepositoryImpl
import com.numero.sojodia.repository.ConfigRepository
import com.numero.sojodia.repository.IConfigRepository

class SojoDiaApplication : Application(), Module {

    override lateinit var configRepository: IConfigRepository

    override lateinit var busDataRepository: BusDataRepository

    override fun onCreate() {
        super.onCreate()
        configRepository = ConfigRepository(this)

        busDataRepository = BusDataRepositoryImpl(this)
    }
}
