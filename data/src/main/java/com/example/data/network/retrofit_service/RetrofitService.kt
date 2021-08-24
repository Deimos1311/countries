package com.example.data.network.retrofit_service

import com.example.data.model.CountryModel
import com.example.data.network.NetConstants
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET(NetConstants.GET_COUNTRY_DATE)
    fun getCountryDate(): Flowable<MutableList<CountryModel>>

    @GET(NetConstants.GET_COUNTRY_BY_NAME)
    fun getCountryByName(@Path(NetConstants.PATH_VARIABLE) name: String): Flowable<MutableList<CountryModel>>
}