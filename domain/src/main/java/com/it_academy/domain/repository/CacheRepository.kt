package com.it_academy.domain.repository

import com.it_academy.domain.dto.countries.CountryDTO
import io.reactivex.rxjava3.core.Flowable

interface CacheRepository {

    fun addAllCountries(list: MutableList<CountryDTO>): Flowable<MutableList<CountryDTO>>

    fun addFilteredCountries(list: MutableList<CountryDTO>) : Flowable<MutableList<CountryDTO>>

    fun getAllCountries(): Flowable<MutableList<CountryDTO>>

    fun getFilteredCountries(): Flowable<MutableList<CountryDTO>>
}