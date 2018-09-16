package com.numero.sojodia

import android.app.Application
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.ConfigRepository
import com.numero.sojodia.repository.IBusDataRepository
import com.numero.sojodia.repository.IConfigRepository
import com.numero.sojodia.resource.ResourceConfig

class SojoDiaApplication : Application(), IApplication {

    override lateinit var configRepository: IConfigRepository

    override lateinit var busDataRepository: IBusDataRepository

    override fun onCreate() {
        super.onCreate()
        configRepository = ConfigRepository(this)

        val busDataApi = ResourceConfig.createBusDataApi(BuildConfig.BUS_DATA_URL)
        busDataRepository = BusDataRepository(this, busDataApi)
    }
}
