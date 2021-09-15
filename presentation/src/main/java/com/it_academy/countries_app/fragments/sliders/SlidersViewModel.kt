package com.it_academy.countries_app.fragments.sliders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.it_academy.countries_app.base.mvvm.BaseViewModel
import com.it_academy.countries_app.base.mvvm.executeJob
import com.it_academy.domain.dto.countries.CountryDTO
import com.it_academy.domain.outcome.Outcome
import com.it_academy.domain.usecase.impl.network.GetAllCountriesFromAPIUseCase

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