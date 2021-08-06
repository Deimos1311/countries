package com.example.test_app.fragments.list_of_countries

import com.example.test_app.base.mvp.BaseMvpView
import com.example.test_app.dto.CountryDTO
import com.example.test_app.model.Country
import com.example.test_app.room.entity.CountryEntity
import com.example.test_app.room.entity.CountryLanguageCrossRef
import com.example.test_app.room.entity.LanguagesListEntity

interface ListOfCountriesView : BaseMvpView {

    fun populateDatabases(
        listOfCountryEntities: MutableList<CountryEntity>,
        listOfLanguagesEntities: MutableList<LanguagesListEntity>,
        crossRef: MutableList<CountryLanguageCrossRef>)

    fun showListOfCountries(listOfCountries: MutableList<CountryDTO>)
}
