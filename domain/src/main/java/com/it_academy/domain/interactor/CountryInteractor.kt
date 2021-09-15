package com.it_academy.domain.interactor

import com.it_academy.domain.dto.countries.CountryDTO
import io.reactivex.rxjava3.core.Flowable

interface CountryInteractor {

    fun getCountryChannel(): Flowable<MutableList<CountryDTO>>

    fun requestAllCountries(): Flowable<Any>

    fun requestCountriesByName(name: String): Flowable<Any>

}