package com.it_academy.data.network.retrofit_service

import com.it_academy.data.models.countriesAPI.CountryModel
import com.it_academy.data.network.CountriesNetConstants
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET(CountriesNetConstants.GET_COUNTRY_DATE)
    fun getCountryDate(): Flowable<MutableList<CountryModel>>

    @GET(CountriesNetConstants.GET_COUNTRY_BY_NAME)
    fun getCountryByName(@Path(CountriesNetConstants.PATH_VARIABLE) name: String): Flowable<MutableList<CountryModel>>
}