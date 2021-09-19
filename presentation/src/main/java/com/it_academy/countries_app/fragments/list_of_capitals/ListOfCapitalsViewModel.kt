package com.it_academy.countries_app.fragments.list_of_capitals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.it_academy.countries_app.base.mvvm.BaseViewModel
import com.it_academy.domain.dto.countries.CapitalDTO
import com.it_academy.domain.outcome.Outcome
import com.it_academy.domain.repository.CountriesFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListOfCapitalsViewModel(
    savedStateHandle: SavedStateHandle,
    private val countriesFlowRepository: CountriesFlowRepository
): BaseViewModel(savedStateHandle) {

    val sharedFlow = MutableSharedFlow<Long>()

    fun getCapitals(): Flow<Outcome<MutableList<CapitalDTO>>> =
        countriesFlowRepository.getCapitals()

    fun getCapitalByName(query: String): Flow<Outcome<MutableList<CapitalDTO>>> =
        countriesFlowRepository.getCapitalByName(query)


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