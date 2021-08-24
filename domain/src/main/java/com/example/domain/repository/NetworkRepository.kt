package com.example.domain.repository

import com.example.domain.dto.CountryDTO
import io.reactivex.rxjava3.core.Flowable

interface NetworkRepository {

    fun getCountryDate(): Flowable<MutableList<CountryDTO>>

    fun getCountryByName(name: String): Flowable<MutableList<CountryDTO>>
}