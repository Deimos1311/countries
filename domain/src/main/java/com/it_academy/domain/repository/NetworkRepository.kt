package com.it_academy.domain.repository

import com.it_academy.domain.dto.countries.CountryDTO
import io.reactivex.rxjava3.core.Flowable

interface NetworkRepository {

    fun getCountryDate(): Flowable<MutableList<CountryDTO>>

    fun getCountryByName(name: String): Flowable<MutableList<CountryDTO>>
}