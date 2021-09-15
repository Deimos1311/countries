package com.it_academy.domain.repository

import com.it_academy.domain.dto.countries.CountryDTO
import com.it_academy.domain.dto.countries.CountryLanguageCrossRefDTO
import com.it_academy.domain.dto.countries.LanguageDTO
import io.reactivex.rxjava3.core.Flowable

interface DatabaseRepository {

    fun addAllCountries(mutableList: MutableList<CountryDTO>)

    fun addLanguage(mutableList: MutableList<LanguageDTO>)

    fun insertCountryLanguageCrossRef(mutableList: MutableList<CountryLanguageCrossRefDTO>)

    fun getAllCountries(): Flowable<MutableList<CountryDTO>>

    fun getLanguages(): Flowable<MutableList<LanguageDTO>>

    fun getCrossRef(): Flowable<MutableList<CountryLanguageCrossRefDTO>>
}