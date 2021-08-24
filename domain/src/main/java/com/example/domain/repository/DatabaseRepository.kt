package com.example.domain.repository

import com.example.domain.dto.CountryDTO
import com.example.domain.dto.CountryLanguageCrossRefDTO
import com.example.domain.dto.LanguageDTO
import io.reactivex.rxjava3.core.Flowable

interface DatabaseRepository {

    fun addAllCountries(mutableList: MutableList<CountryDTO>)

    fun addLanguage(mutableList: MutableList<LanguageDTO>)

    fun insertCountryLanguageCrossRef(mutableList: MutableList<CountryLanguageCrossRefDTO>)

    fun getAllCountries(): Flowable<MutableList<CountryDTO>>

    fun getLanguages(): Flowable<MutableList<LanguageDTO>>

    fun getCrossRef(): Flowable<MutableList<CountryLanguageCrossRefDTO>>
}