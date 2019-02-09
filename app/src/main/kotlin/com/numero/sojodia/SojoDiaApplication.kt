package com.numero.sojodia

import android.app.Application
import com.numero.common.IIntentResolver
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.ConfigRepository
import com.numero.sojodia.repository.IBusDataRepository
import com.numero.sojodia.repository.IConfigRepository
import com.numero.sojodia.resource.BusDataSource

class SojoDiaApplication : Application(), ILegacyModule {

    override lateinit var configRepository: IConfigRepository

    override lateinit var busDataRepository: IBusDataRepository

    override lateinit var intentResolver: IIntentResolver

    override fun onCreate() {
        super.onCreate()
        configRepository = ConfigRepository(this)

        busDataRepository = BusDataRepository(BusDataSource(this))

        intentResolver = IntentResolver(this)
    }
}
