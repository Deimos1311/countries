package com.example.test_app.fragments.list_of_capitals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.DEBOUNCE_TIME
import com.example.domain.dto.CapitalDTO
import com.example.domain.outcome.Outcome
import com.example.domain.repository.FlowRepository
import com.example.test_app.base.mvvm.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListOfCapitalsViewModel(
    savedStateHandle: SavedStateHandle,
    private val flowRepository: FlowRepository
): BaseViewModel(savedStateHandle) {

    val stateFlow = MutableStateFlow("")
    val sharedFlow = MutableSharedFlow<Long>()

    var livaData = MutableLiveData<Outcome<MutableList<CapitalDTO>>>()

    fun getCapitals(): Flow<Outcome<MutableList<CapitalDTO>>> = flowRepository.getCapitals()

    fun getCapitalByName() {
        viewModelScope.launch {
            stateFlow
                .filter { it.length >= 3 }
                .debounce(DEBOUNCE_TIME)
                .distinctUntilChanged()
                .flatMapConcat { string ->
                    flowRepository.getCapitalByName(string)
                }
                .flowOn(Dispatchers.IO)
                .catch { emitAll(flowOf()) }
                .collect {
                    livaData.value = it
                }
        }
    }

    fun clickOnItem() {
        viewModelScope.launch {
            getCapitals().collect {
                if (it is Outcome.Success<MutableList<CapitalDTO>>) {
                    sharedFlow.emit(it.data.size.toLong())
                }
            }
        }
    }
}