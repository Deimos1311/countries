package com.example.domain.interactor

import com.example.domain.dto.CountryDTO
import io.reactivex.rxjava3.core.Flowable

interface CountryInteractor {

    fun getCountryChannel(): Flowable<MutableList<CountryDTO>>

    fun requestAllCountries(): Flowable<Any>

    fun requestCountriesByName(name: String): Flowable<Any>

}