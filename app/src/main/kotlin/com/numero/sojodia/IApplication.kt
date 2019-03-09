package com.numero.sojodia

import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.IConfigRepository

interface IApplication {
    val configRepository: IConfigRepository

    val busDataRepository: BusDataRepository
}