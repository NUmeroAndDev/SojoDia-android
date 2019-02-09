package com.numero.sojodia

import com.numero.common.IModule
import com.numero.sojodia.repository.IBusDataRepository
import com.numero.sojodia.repository.IConfigRepository

interface ILegacyModule : IModule {
    val configRepository: IConfigRepository

    val busDataRepository: IBusDataRepository
}