package com.numero.sojodia

import com.numero.sojodia.repository.IBusDataRepository
import com.numero.sojodia.repository.IConfigRepository

interface IApplication {
    var configRepository: IConfigRepository

    var busDataRepository: IBusDataRepository
}