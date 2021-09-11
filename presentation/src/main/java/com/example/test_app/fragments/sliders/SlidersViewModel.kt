package com.example.test_app.fragments.sliders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.domain.dto.CountryDTO
import com.example.domain.usecase.impl.network.GetAllCountriesFromAPIUseCase
import com.example.test_app.base.mvvm.BaseViewModel
import com.example.domain.outcome.Outcome
import com.example.test_app.base.mvvm.executeJob

class SlidersViewModel(
    savedStateHandle: SavedStateHandle,
    private val mGetAllCountriesFromAPIUseCase: GetAllCountriesFromAPIUseCase,
) : BaseViewModel(savedStateHandle) {

    var getListOfCountriesLivaData = MutableLiveData<Outcome<MutableList<CountryDTO>>>()
    var getListOfCountriesSortedByAreaLivaData = MutableLiveData<Outcome<MutableList<CountryDTO>>>()

    fun getCountriesListSortedByPopulation() {
        addToDisposable(
            executeJob(
                mGetAllCountriesFromAPIUseCase.execute()
                    .doOnNext { response ->
                        response.sortBy { population ->
                            population.population
                        }
                    },
                getListOfCountriesLivaData
            )
        )
    }

    fun getCountriesListSortedByArea() {
        addToDisposable(
            executeJob(
                mGetAllCountriesFromAPIUseCase.execute()
                    .doOnNext { response ->
                        response.sortBy { area ->
                            area.area
                        }
                    },
                getListOfCountriesSortedByAreaLivaData
            )
        )
    }
}