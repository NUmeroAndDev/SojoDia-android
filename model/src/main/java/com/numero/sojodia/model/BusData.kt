package com.numero.sojodia.model

data class BusData(
        val tkBusTimeListGoing: List<BusTime>,
        val tkBusTimeListReturn: List<BusTime>,
        val tndBusTimeListGoing: List<BusTime>,
        val tndBusTimeListReturn: List<BusTime>
) {
    val isNoBusData: Boolean
        get() {
            return tkBusTimeListGoing.isEmpty() and
                    tkBusTimeListReturn.isEmpty() and
                    tndBusTimeListGoing.isEmpty() and
                    tndBusTimeListReturn.isEmpty()
        }
}