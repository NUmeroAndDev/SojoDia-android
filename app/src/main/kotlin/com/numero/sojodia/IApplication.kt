package com.numero.sojodia

import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.ConfigRepository

interface IApplication {
    val configRepository: ConfigRepository

    val busDataRepository: BusDataRepository
}