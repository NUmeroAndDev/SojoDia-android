package com.numero.sojodia.ui.board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.numero.sojodia.model.*
import com.numero.sojodia.repository.BusDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BusBoardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BusBoardUiState.empty())
    val uiState: StateFlow<BusBoardUiState> = _uiState

    private var week = Week.getCurrentWeek()

    fun fetchBusData(
        busDataRepository: BusDataRepository,
        reciprocate: Reciprocate
    ) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.Main) {
                    val busData = busDataRepository.getBusData()
                    val tkTimeList = busData.filterTkList(reciprocate, week)
                    val tndTimeList = busData.filterTndList(reciprocate, week)
                    val nearTkBusTime = tkTimeList.findNearBusTime(Time())
                    val nearTndBusTime = tndTimeList.findNearBusTime(Time())

                    _uiState.value = uiState.value.copy(
                        tkBusBoardSchedule = BusBoardSchedule(
                            route = Route.TK,
                            nearBusTime = nearTkBusTime,
                            null
                        ),
                        tndBusBoardSchedule = BusBoardSchedule(
                            route = Route.TND,
                            nearBusTime = nearTndBusTime,
                            null
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun BusData.filterTkList(
        reciprocate: Reciprocate,
        week: Week
    ): TkBusTimeList {
        val list = when (reciprocate) {
            Reciprocate.GOING -> tkBusTimeListGoing
                .asSequence()
                .filter { it.week == week }
                .toList()
            Reciprocate.RETURN -> tkBusTimeListReturn
                .asSequence()
                .filter { it.week == week }
                .toList()
        }
        return TkBusTimeList(list)
    }

    private fun BusData.filterTndList(
        reciprocate: Reciprocate,
        week: Week
    ): TndBusTimeList {
        val list = when (reciprocate) {
            Reciprocate.GOING -> tndBusTimeListGoing
                .asSequence()
                .filter { busTime -> busTime.week == week }
                .toList()
            Reciprocate.RETURN -> tndBusTimeListReturn
                .asSequence()
                .filter { busTime -> busTime.week == week }
                .toList()
        }
        return TndBusTimeList(list)
    }
}