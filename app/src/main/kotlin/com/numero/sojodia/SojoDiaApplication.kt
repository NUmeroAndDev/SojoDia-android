package com.numero.sojodia

import android.app.Application
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.ConfigRepository
import com.numero.sojodia.repository.IBusDataRepository
import com.numero.sojodia.repository.IConfigRepository
import com.numero.sojodia.resource.BusDataSource

class SojoDiaApplication : Application(), IApplication {

    override lateinit var configRepository: IConfigRepository

    override lateinit var busDataRepository: IBusDataRepository

    override fun onCreate() {
        super.onCreate()
        configRepository = ConfigRepository(this)

        busDataRepository = BusDataRepository(BusDataSource(this))
    }
}
