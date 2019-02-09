package com.numero.common

import com.numero.sojodia.repository.IBusDataRepository
import com.numero.sojodia.repository.IConfigRepository

interface IModule {
    val configRepository: IConfigRepository

    val busDataRepository: IBusDataRepository

    val intentResolver: IIntentResolver
}