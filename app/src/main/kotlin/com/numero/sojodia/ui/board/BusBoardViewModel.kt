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
                    val current = Time()
                    val busData = busDataRepository.getBusData()
                    val tkTimeList = busData.filterTkList(reciprocate, week)
                    val tndTimeList = busData.filterTndList(reciprocate, week)
                    val nearTkBusTime = tkTimeList.findNearBusTime(current)
                    val nearTndBusTime = tndTimeList.findNearBusTime(current)
                    val nextTkBusTime = tkTimeList.findNextBusTime(current)
                    val nextTndBusTime = tndTimeList.findNextBusTime(current)

                    _uiState.value = uiState.value.copy(
                        tkBusBoardSchedule = BusBoardSchedule(
                            route = Route.TK,
                            nearBusTime = nearTkBusTime,
                            nextBusTime = nextTkBusTime,
                            canPrevBusTime = true,
                            canNextBusTime = true
                        ),
                        tndBusBoardSchedule = BusBoardSchedule(
                            route = Route.TND,
                            nearBusTime = nearTndBusTime,
                            nextBusTime = nextTndBusTime,
                            canPrevBusTime = true,
                            canNextBusTime = true
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun prevTkBusTime() {
    }

    fun nextTkBusTime() {
        // FIXME example
        val tkBusSchedule = uiState.value.tkBusBoardSchedule
        _uiState.value = uiState.value.copy(
            tkBusBoardSchedule = tkBusSchedule.copy(
                nearBusTime = tkBusSchedule.nextBusTime,
                nextBusTime = null,
                canPrevBusTime = false,
                canNextBusTime = false
            )
        )
    }

    fun prevTndBusTime() {
    }

    fun nextTndBusTime() {
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