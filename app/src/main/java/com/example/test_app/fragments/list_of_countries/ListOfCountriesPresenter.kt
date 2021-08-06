package com.example.test_app.fragments.list_of_countries

import com.example.test_app.RETRY
import com.example.test_app.base.mvp.BaseMvpPresenter
import com.example.test_app.common.Common
import com.example.test_app.dto.CountryDTO
import com.example.test_app.model.Country
import com.example.test_app.room.entity.CountryEntity
import com.example.test_app.room.entity.CountryLanguageCrossRef
import com.example.test_app.room.entity.LanguagesListEntity
import com.example.test_app.transformers.transform
import com.example.test_app.transformers.transformToMutableList
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit


class ListOfCountriesPresenter : BaseMvpPresenter<ListOfCountriesView>() {

    var listOfCountryEntities: MutableList<CountryEntity> = mutableListOf()
    private val listOfLanguagesEntities: MutableList<LanguagesListEntity> = mutableListOf()
    private val crossRef: MutableList<CountryLanguageCrossRef> = mutableListOf()

    fun getListOfCountries() {

        addDisposable(
            inBackground(Common.retrofitService?.getCountryDate())
                ?.retry(RETRY)
                ?.map { it.transformToMutableList() }
                ?.subscribe({ response ->
                    getView()?.showListOfCountries(response)
                }, { throwable ->
                    throwable.printStackTrace()
                    getView()?.showError("Get list of countries ERROR!")
                })
        )
    }

    fun addListOfCountriesToDatabase() {

        inBackground(Common.retrofitService?.getCountryDate())
            ?.map { it.transformToMutableList() } //???????????????????????????? which implementation
            ?.subscribe({ response ->
                response.forEach {
                    listOfCountryEntities.add(
                        CountryEntity(
                            it.countryName,
                            it.cityName,
                            it.population
                        )
                    )
                    (it.languages.transformToMutableList().forEach { item ->
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
                response.forEach {
                    crossRef.add(
                        CountryLanguageCrossRef(
                            it.countryName,
                            it.languages.joinToString { item ->
                                item.name ?: ""
                            }
                        )
                    )
                }
                getView()?.populateDatabases(
                    listOfCountryEntities, listOfLanguagesEntities, crossRef
                )
            }, { throwable ->
                throwable.printStackTrace()
                getView()?.showError("Add List Of Countries To Database ERROR")
            })
    }
}