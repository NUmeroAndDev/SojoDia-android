package com.numero.sojodia.repository

import com.numero.sojodia.model.BusTime

interface IBusDataRepository {

    var tkBusTimeListGoing: MutableList<BusTime>

    var tkBusTimeListReturn: MutableList<BusTime>

    var tndBusTimeListGoing: MutableList<BusTime>

    var tndBusTimeListReturn: MutableList<BusTime>

    fun clearCache()

}
