package com.example.test_app.fragments.list_of_countries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.test_app.CountryApp
import com.example.test_app.DEBOUNCE_TIME
import com.example.test_app.MIN_SEARCH_STRING_LENGTH
import com.example.test_app.base.mvvm.BaseViewModel
import com.example.test_app.base.mvvm.Outcome
import com.example.test_app.base.mvvm.executeJob
import com.example.test_app.common.Common
import com.example.test_app.dto.CountryDTO
import com.example.test_app.room.CountryDAO
import com.example.test_app.room.entity.CountryEntity
import com.example.test_app.room.entity.CountryLanguageCrossRef
import com.example.test_app.room.entity.LanguagesListEntity
import com.example.test_app.transformers.transformToMutableListDto
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class ListOfCountriesViewModel(savedStateHandle: SavedStateHandle) :
    BaseViewModel(savedStateHandle) {

    //val countryLiveData = MutableLiveData<Outcome<CountryDTO>>()
    var searchSubject: BehaviorSubject<String> = BehaviorSubject.create()

    var listOfCountriesLiveData = MutableLiveData<Outcome<MutableList<CountryDTO>>>()
    var countryListDatabaseLiveData = MutableLiveData<Outcome<MutableList<CountryDTO>>>()

    var listOfCountryEntities: MutableList<CountryEntity> = mutableListOf()
    private val listOfLanguagesEntities: MutableList<LanguagesListEntity> = mutableListOf()
    private val crossRef: MutableList<CountryLanguageCrossRef> = mutableListOf()

    var daoCountry: CountryDAO? = null

    var searchList: MutableList<CountryDTO> = mutableListOf()
    var tempList: MutableList<CountryDTO> = mutableListOf()

    /*fun getCountryByName() {

        addToDisposable(
            executeJob(
                Common.retrofitService!!.getCountryByName("Belarus")
                    .map { it[0].transform() }, countryLiveData
            )
        )
    }*/

    fun getListOfCountries() {

        addToDisposable(
            executeJob(
                Common.retrofitService!!.getCountryDate()
                    .map { it.transformToMutableListDto() }, listOfCountriesLiveData
            )
        )
    }

    fun addListOfCountriesToDatabase(data: MutableList<CountryDTO>) {

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
        CountryApp.daoCountry?.addAllCountries(listOfCountryEntities)
        CountryApp.daoCountry?.insertCountryLanguageCrossRef(crossRef)
        CountryApp.daoCountry?.addLanguage(listOfLanguagesEntities)
    }

    /*fun instantSearch() : Flowable<MutableList<CountryDTO>> {

            .observeOn(Schedulers.io())
            .filter { it.length >= MIN_SEARCH_STRING_LENGTH }
            .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .flatMap {
                Common.retrofitService?.getCountryByName(it)?.toObservable()
                    ?.onErrorResumeNext { Observable.just(mutableListOf()) }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                Log.d(ContentValues.TAG, "subscriber: $text")
            }
    }*/
}

