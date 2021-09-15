package com.it_academy.countries_app.fragments.list_of_capitals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.it_academy.countries_app.base.mvvm.BaseViewModel
import com.it_academy.domain.DEBOUNCE_TIME
import com.it_academy.domain.dto.countries.CapitalDTO
import com.it_academy.domain.outcome.Outcome
import com.it_academy.domain.repository.CountriesFlowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListOfCapitalsViewModel(
    savedStateHandle: SavedStateHandle,
    private val countriesFlowRepository: CountriesFlowRepository
): BaseViewModel(savedStateHandle) {

    val stateFlow = MutableStateFlow("")
    val sharedFlow = MutableSharedFlow<Long>()

    var livaData = MutableLiveData<Outcome<MutableList<CapitalDTO>>>()

    fun getCapitals(): Flow<Outcome<MutableList<CapitalDTO>>> = countriesFlowRepository.getCapitals()

    fun getCapitalByName() {
        viewModelScope.launch {
            stateFlow
                .filter { it.length >= 3 }
                .debounce(DEBOUNCE_TIME)
                .distinctUntilChanged()
                .flatMapConcat { string ->
                    countriesFlowRepository.getCapitalByName(string)
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