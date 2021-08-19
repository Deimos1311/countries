package com.example.test_app.repository.networkRepo

import com.example.test_app.NetConstants
import com.example.test_app.dto.CountryDTO
import com.example.test_app.model.Country
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkRepository {

    fun getCountryDate(): Flowable<MutableList<CountryDTO>>

    fun getCountryByName(name: String): Flowable<MutableList<CountryDTO>>
}