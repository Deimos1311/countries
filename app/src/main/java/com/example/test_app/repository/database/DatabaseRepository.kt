package com.example.test_app.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.test_app.base.mvvm.Outcome
import com.example.test_app.room.entity.CountryEntity
import com.example.test_app.room.entity.CountryLanguageCrossRef
import com.example.test_app.room.entity.LanguagesListEntity
import io.reactivex.rxjava3.core.Flowable

interface DatabaseRepository {

    fun addAllCountries(mutableList: MutableList<CountryEntity>)

    fun addLanguage(mutableList: MutableList<LanguagesListEntity>)

    fun insertCountryLanguageCrossRef(mutableList: MutableList<CountryLanguageCrossRef>)

    fun getAllCountries(): Flowable<MutableList<CountryEntity>>

    fun getLanguages(): Flowable<MutableList<LanguagesListEntity>>

    fun getCrossRef(): LiveData<MutableList<CountryLanguageCrossRef>>
}