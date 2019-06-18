package com.numero.sojodia

import android.app.Application
import com.numero.sojodia.extension.applyApplication
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.BusDataRepositoryImpl
import com.numero.sojodia.repository.ConfigRepository
import com.numero.sojodia.repository.ConfigRepositoryImpl

class SojoDiaApplication : Application(), Module {

    override lateinit var configRepository: ConfigRepository

    override lateinit var busDataRepository: BusDataRepository

    override fun onCreate() {
        super.onCreate()
        configRepository = ConfigRepositoryImpl(this)

        busDataRepository = BusDataRepositoryImpl(this)
        configRepository.appTheme.applyApplication()
    }
}
