package com.example.test_app.fragments.list_of_countries

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.domain.dto.CountryDTO
import com.example.domain.dto.CountryLanguageCrossRefDTO
import com.example.domain.dto.LanguageDTO
import com.example.domain.usecase.impl.database.*
import com.example.domain.usecase.impl.network.GetAllCountriesFromAPIUseCase
import com.example.domain.usecase.impl.network.GetCountryListByNameFromAPIUseCase
import com.example.test_app.DEBOUNCE_TIME
import com.example.test_app.MIN_SEARCH_STRING_LENGTH
import com.example.test_app.base.mvvm.BaseViewModel
import com.example.test_app.base.mvvm.Outcome
import com.example.test_app.base.mvvm.executeJob
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
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
    /*filter: FilterRepository*/
) :
    BaseViewModel(savedStateHandle) {

    var searchSubject: BehaviorSubject<String> = BehaviorSubject.create()


    var listOfCountriesLiveData = MutableLiveData<Outcome<MutableList<CountryDTO>>>()
    var listOfCountriesAddToDBLiveData = MutableLiveData<Outcome<MutableList<CountryDTO>>>()
    var listOfCountriesGetFromDBLiveData = MutableLiveData<Outcome<MutableList<CountryDTO>>>()
    var listOfLanguagesGetFromDBLiveData = MutableLiveData<Outcome<MutableList<LanguageDTO>>>()

    var listof = MutableLiveData<Outcome<MutableList<CountryDTO>>>()

    var listOfCountryDB: MutableList<CountryDTO> = mutableListOf()
    var listOfLanguagesDB: MutableList<LanguageDTO> = mutableListOf()
    var crossRefDB: MutableList<CountryLanguageCrossRefDTO> = mutableListOf()

    fun getListOfCountries() {

        addToDisposable(
            executeJob(
                mGetAllCountriesFromAPIUseCase.execute(),
                listOfCountriesLiveData
            )
        )
    }

    fun addListOfCountriesToDB() {

        addToDisposable(
            executeJob(
                mGetAllCountriesFromAPIUseCase.execute()
                    .doOnNext { data ->
                        data.forEach {
                            listOfCountryDB.add(
                                CountryDTO(
                                    it.countryName,
                                    it.cityName,
                                    it.population,
                                    mutableListOf()
                                )
                            )
                            it.languages.forEach { language ->
                                listOfLanguagesDB.add(
                                    LanguageDTO(
                                        language.name,
                                        language.nativeName,
                                        language.iso639_1,
                                        language.iso639_2
                                    )
                                )
                            }
                        }
                        data.forEach {
                            crossRefDB.add(
                                CountryLanguageCrossRefDTO(
                                    it.countryName,
                                    it.languages.joinToString { item ->
                                        item.name
                                    }
                                )
                            )
                        }
                        mAddAllCountriesToDBUseCase.setParams(listOfCountryDB).execute()
                        mAddLanguageToDBUseCase.setParams(listOfLanguagesDB).execute()
                        mAddCountryLanguageCrossRefToDBUseCase.setParams(crossRefDB).execute()
                    },
                listOfCountriesAddToDBLiveData
            )
        )
    }

    fun getListOfCountriesFromDB() {
        addToDisposable(
            executeJob(
                mGetAllCountriesFromDBUseCase.execute(),
                listOfCountriesGetFromDBLiveData
            )
        )
    }

    fun getListOfLanguagesFromDB() {
        addToDisposable(
            executeJob(
                mGetLanguageFromDBUseCase.execute(),
                listOfLanguagesGetFromDBLiveData
            )
        )
    }

    fun instantSearch(
        searchListByName: MutableList<CountryDTO>,
        tempListByName: MutableList<CountryDTO>
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
            .subscribe { text ->
                Log.d(TAG, "subscriber: $text")
            }
    }

    //todo remake
    fun searchByPopulation(
        searchListByPopulation: MutableList<CountryDTO>,
        populationFromSlider: MutableList<Float>,
        sortedList: MutableList<CountryDTO>
    ) {
        val sear: Flowable<MutableList<CountryDTO>> = Flowable.just(searchListByPopulation)

        addToDisposable(
            executeJob(
                sear
                    .flatMap {
                        Flowable.fromIterable(it)
                            .filter { item ->
                                populationFromSlider[0] < item.population
                            }
                            .filter { item ->
                                populationFromSlider[1] > item.population
                            }
                    }
                    .flatMap {
                        Flowable.just(mutableListOf(it))
                    }

                /*.flatMap {
                    Flowable.fromIterable(it)
                        .filter { item ->
                            populationFromSlider[0] < item.population
                        }
                        .filter { item ->
                            populationFromSlider[1] > item.population
                        }
                }*/, listof
            )
        )
/*                    .observeOn(Schedulers.io())
                    .flatMap {
                        Flowable.fromIterable(it)
                            .filter { item ->
                                populationFromSlider[0] < item.population
                            }
                            .filter { item ->
                                populationFromSlider[1] > item.population
                            }
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io()), listof))*/
/*            .subscribe {
                sortedList.add(it)
                Log.d(TAG, "subscriber: $it")*/
    }
    }


