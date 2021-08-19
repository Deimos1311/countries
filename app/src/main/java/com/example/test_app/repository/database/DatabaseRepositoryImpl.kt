package com.example.test_app.repository.database

import androidx.lifecycle.LiveData
import com.example.test_app.base.mvvm.Outcome
import com.example.test_app.room.CountryDatabase
import com.example.test_app.room.entity.CountryEntity
import com.example.test_app.room.entity.CountryLanguageCrossRef
import com.example.test_app.room.entity.LanguagesListEntity
import io.reactivex.rxjava3.core.Flowable

class DatabaseRepositoryImpl(private val database: CountryDatabase) : DatabaseRepository {
    override fun addAllCountries(mutableList: MutableList<CountryEntity>) =
        database.countryDAO.addAllCountries(mutableList)

    override fun addLanguage(mutableList: MutableList<LanguagesListEntity>) =
        database.countryDAO.addLanguage(mutableList)

    override fun insertCountryLanguageCrossRef(mutableList: MutableList<CountryLanguageCrossRef>) =
        database.countryDAO.insertCountryLanguageCrossRef(mutableList)

    override fun getAllCountries(): Flowable<MutableList<CountryEntity>> =
        database.countryDAO.getAllCountries()

    override fun getLanguages(): Flowable<MutableList<LanguagesListEntity>> =
        database.countryDAO.getLanguages()

    override fun getCrossRef(): LiveData<MutableList<CountryLanguageCrossRef>> =
        database.countryDAO.getCrossRef()
}