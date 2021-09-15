package com.it_academy.countries_app.fragments.list_of_countries

import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.it_academy.countries_app.base.mvvm.BaseViewModel
import com.it_academy.countries_app.base.mvvm.executeJob
import com.it_academy.countries_app.base.mvvm.executeJobWithoutProgress
import com.it_academy.domain.DEBOUNCE_TIME
import com.it_academy.domain.MIN_SEARCH_STRING_LENGTH
import com.it_academy.domain.ONE_KILOMETER
import com.it_academy.domain.dto.countries.CountryDTO
import com.it_academy.domain.dto.countries.CountryLanguageCrossRefDTO
import com.it_academy.domain.dto.countries.LanguageDTO
import com.it_academy.domain.interactor.CountryInteractor
import com.it_academy.domain.outcome.Outcome
import com.it_academy.domain.usecase.impl.database.*
import com.it_academy.domain.usecase.impl.network.GetAllCountriesFromAPIUseCase
import com.it_academy.domain.usecase.impl.network.GetCountryListByNameFromAPIUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class ListOfCountriesViewModel(
    savedStateHandle: SavedStateHandle,
    private val mGetAllCountriesFromAPIUseCase: GetAllCountriesFromAPIUseCase,
    private val mGetCountryListByNameFromAPIUseCase: GetCountryListByNameFromAPIUseCase,
    private val mGetAllCountriesFromDBUseCase: GetAllCountriesFromDBUseCase,
    private val mGetLanguageFromDBUseCase: GetLanguageFromDBUseCase,
    private val mAddAllCountriesToDBUseCase: AddAllCountriesToDBUseCase,
    private val mAddLanguageToDBUseCase: AddLanguageToDBUseCase,
    private val mAddCountryLanguageCrossRefToDBUseCase: AddCountryLanguageCrossRefToDBUseCase,
    private val mCountryInteractor: CountryInteractor
    /*filter: FilterRepository*/
) :
    BaseViewModel(savedStateHandle) {

    var searchSubject: BehaviorSubject<String> = BehaviorSubject.create()

    var dataLiveData = MutableLiveData<Outcome<MutableList<CountryDTO>>>()
    var listOfCountriesAddToDBLiveData = MutableLiveData<Outcome<MutableList<CountryDTO>>>()
    var listOfCountriesGetFromDBLiveData = MutableLiveData<Outcome<MutableList<CountryDTO>>>()
    var listOfLanguagesGetFromDBLiveData = MutableLiveData<Outcome<MutableList<LanguageDTO>>>()

    var sortedListLiveData = MutableLiveData<Outcome<MutableList<CountryDTO>>>()

    var listOfCountryDB: MutableList<CountryDTO> = mutableListOf()
    var listOfLanguagesDB: MutableList<LanguageDTO> = mutableListOf()
    var crossRefDB: MutableList<CountryLanguageCrossRefDTO> = mutableListOf()

    var dataStatesLivaData = MutableLiveData<Outcome<MutableList<CountryDTO>>>()

    private var userLocation: Location? = null

    var tempListByName: MutableList<CountryDTO> = mutableListOf()

    fun getListOfCountries() {
        addToDisposable(
            executeJob(
                mCountryInteractor.requestAllCountries().map { mutableListOf() },
                dataStatesLivaData
            )
        )
    }

    fun subscribeCountryChannel() {
        addToDisposable(
            executeJobWithoutProgress(
                mCountryInteractor.getCountryChannel()
                    .doOnNext {
                        tempListByName = it
                    }
                    //todo doesnt work correctly(missing location after fr B -> A)
                    .doOnNext {
                        it.forEach { country ->
                            country.distance =
                                calculateDistanceFromUserToCountry(country).toInt()
                        }
                    }, dataLiveData
            )
        )
    }

    /*fun getListOfCountriesFromDB() {
        addToDisposable(
            executeJob(
                mGetAllCountriesFromDBUseCase.execute(),
                listOfCountriesGetFromDBLiveData
            )
        )
    }*/

    /*fun getListOfLanguagesFromDB() {
        addToDisposable(
            executeJob(
                mGetLanguageFromDBUseCase.execute(),
                listOfLanguagesGetFromDBLiveData
            )
        )
    }*/

    fun instantSearch(
        searchListByName: MutableList<CountryDTO>
    ) {
        searchSubject
            .doOnNext { query ->
                searchListByName.clear()

                if (query.lowercase().isNotEmpty()) {
                    tempListByName.forEach {
                        if (it.countryName.lowercase().contains(query)) {
                            searchListByName.add(it)
                        }
                    }
                } else {
                    searchListByName.addAll(tempListByName)
                }
            }
            .filter { it.length >= MIN_SEARCH_STRING_LENGTH }
            .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .flatMap {
                mGetCountryListByNameFromAPIUseCase.setParams(it).execute()
                    .toObservable()
                    .onErrorResumeNext { Observable.just(mutableListOf()) }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
    }

    fun attachCurrentLocation(location: Location) {
        userLocation = location
    }

    fun searchBySliderFragment(
        populationStart: Float,
        populationEnd: Float,
        areaStart: Float,
        areaEnd: Float,
        distance: Float,
    ) {
        addToDisposable(
            executeJob(
                mGetAllCountriesFromAPIUseCase.execute()
                    .map {
                        it.filter { itemPopulation ->
                            itemPopulation
                                .population
                                .toFloat() in populationStart..populationEnd
                        }
                            .toMutableList()
                    }
                    .map {
                        it.filter { itemArea ->
                            itemArea
                                .area
                                .toFloat() in areaStart..areaEnd
                        }
                            .toMutableList()

                    }
                    .map {
                        it.filter { itemLocation ->
                            calculateDistanceFromUserToCountry(itemLocation) <= distance
                        }
                            .toMutableList()
                    }
                    .doOnNext {
                        it.forEach { country ->
                            country.distance =
                                calculateDistanceFromUserToCountry(country).toInt()
                        }
                    }, dataLiveData
            )
        )
    }

    private fun calculateDistanceFromUserToCountry(countryDTO: CountryDTO): Float {
        var result = 0.0F
        if (countryDTO.location.isNotEmpty()) {
            var currentCountryLocation =
                Location(LocationManager.GPS_PROVIDER).apply {
                    latitude = countryDTO.location[0]
                    longitude = countryDTO.location[1]
                }
            //Log.e("userLocation", "")
            userLocation?.let {
                result = it.distanceTo(currentCountryLocation) / ONE_KILOMETER
            }
        }
        return result
    }
}



