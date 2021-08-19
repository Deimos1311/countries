package com.example.test_app.fragments.list_of_countries

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.test_app.DEBOUNCE_TIME
import com.example.test_app.MIN_SEARCH_STRING_LENGTH
import com.example.test_app.base.mvvm.BaseViewModel
import com.example.test_app.base.mvvm.Outcome
import com.example.test_app.base.mvvm.executeJob
import com.example.test_app.common.Common
import com.example.test_app.dto.CountryDTO
import com.example.test_app.repository.database.DatabaseRepository
import com.example.test_app.repository.networkRepo.NetworkRepository
import com.example.test_app.room.entity.CountryEntity
import com.example.test_app.room.entity.CountryLanguageCrossRef
import com.example.test_app.room.entity.LanguagesListEntity
import com.example.test_app.transformers.transformToMutableListDto
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class ListOfCountriesViewModel(
    savedStateHandle: SavedStateHandle,
    private val databaseRepository: DatabaseRepository,
    private val networkRepository: NetworkRepository
    /*filter: FilterRepository*/
) :
    BaseViewModel(savedStateHandle) {

    var searchSubject: BehaviorSubject<String> = BehaviorSubject.create()

    var listOfCountriesLiveData = MutableLiveData<Outcome<MutableList<CountryDTO>>>()
    var listOfCountriesAddToDBLiveData = MutableLiveData<Outcome<MutableList<CountryDTO>>>()
    var listOfCountriesGetFromDBLiveData = MutableLiveData<Outcome<MutableList<CountryEntity>>>()
    var listOfLanguagesGetFromDBLiveData = MutableLiveData<Outcome<MutableList<LanguagesListEntity>>>()

    var listOfCountryEntities: MutableList<CountryEntity> = mutableListOf()
    var listOfLanguagesEntities: MutableList<LanguagesListEntity> = mutableListOf()
    var crossRef: MutableList<CountryLanguageCrossRef> = mutableListOf()

    fun getListOfCountries() {

        addToDisposable(
            executeJob(
                networkRepository.getCountryDate(),
                listOfCountriesLiveData
            )
        )
    }

    fun addListOfCountriesToDB() {

        addToDisposable(
            executeJob(
                networkRepository.getCountryDate()
                    .doOnNext { data ->
                        data.forEach {
                            listOfCountryEntities.add(
                                CountryEntity(
                                    it.countryName,
                                    it.cityName,
                                    it.population
                                )
                            )
                            (it.languages.transformToMutableListDto().forEach { item ->
                                listOfLanguagesEntities.add(
                                    LanguagesListEntity(
                                        item.iso639_1,
                                        item.iso639_2,
                                        item.name,
                                        item.nativeName
                                    )
                                )
                            })
                        }
                        data.forEach {
                            crossRef.add(
                                CountryLanguageCrossRef(
                                    it.countryName,
                                    it.languages.joinToString { item ->
                                        item.name.toString()
                                    }
                                )
                            )
                        }
                        databaseRepository.addAllCountries(listOfCountryEntities)
                        databaseRepository.insertCountryLanguageCrossRef(crossRef)
                        databaseRepository.addLanguage(listOfLanguagesEntities)
                    },
                listOfCountriesAddToDBLiveData
            )
        )
    }

    fun getListOfCountriesFromDB() {
        addToDisposable(
            executeJob(
                databaseRepository.getAllCountries(),
                listOfCountriesGetFromDBLiveData
            )
        )
    }

    fun getListOfLanguagesFromDB() {
        addToDisposable(
            executeJob(
                databaseRepository.getLanguages(),
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
                Common.retrofitService?.getCountryByName(it)?.toObservable()
                    ?.onErrorResumeNext { Observable.just(mutableListOf()) }
            }
            .map { it.transformToMutableListDto() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                Log.d(ContentValues.TAG, "subscriber: $text")
            }
    }

    //todo remake
    fun searchByPopulation(
        searchListByPopulation: MutableList<CountryDTO>,
        populationFromSlider: MutableList<Float>,
        sortedList: MutableList<CountryDTO>
    ) {
        Flowable.just(searchListByPopulation)
            .observeOn(Schedulers.io())
            .flatMap {
                Flowable.fromIterable(it)
                    .filter { item ->
                        populationFromSlider[0] < item.population && populationFromSlider[1] > item.population
                    }
                    .map { th ->
                        sortedList.addAll(listOf(th))
                    }
            }
            /*it.forEach { item ->
                populationFromSlider[0] < item.population && populationFromSlider[1] > item.population
            }
        }*/
/*            .map {
                it.forEach { item ->
                    if (populationFromSlider[0] < item.population && populationFromSlider[1] > item.population) {
                        sortedList.add(item)
                    }
                    return@map
                }
            }*/
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                Log.d(ContentValues.TAG, "subscriber")
            }
    }
}

