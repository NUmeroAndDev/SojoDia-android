package com.numero.sojodia

import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.ConfigRepository

interface Component {
    val configRepository: ConfigRepository

    val busDataRepository: BusDataRepository
}