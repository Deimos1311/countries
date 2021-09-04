package com.example.domain.repository

import com.example.domain.dto.CountryDTO
import io.reactivex.rxjava3.core.Flowable

interface CacheRepository {

    fun addAllCountries(list: MutableList<CountryDTO>): Flowable<MutableList<CountryDTO>>

    fun addFilteredCountries(list: MutableList<CountryDTO>) : Flowable<MutableList<CountryDTO>>

    fun getAllCountries(): Flowable<MutableList<CountryDTO>>

    fun getFilteredCountries(): Flowable<MutableList<CountryDTO>>
}