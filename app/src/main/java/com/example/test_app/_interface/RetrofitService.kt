package com.example.test_app._interface

import com.example.test_app.NetConstants
import com.example.test_app.model.Country
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET(NetConstants.GET_COUNTRY_DATE)
    fun getCountryDate(): Flowable<MutableList<Country>>

    @GET(NetConstants.GET_COUNTRY_BY_NAME)
    fun getCountryByName(@Path(NetConstants.PATH_VARIABLE) name: String): Flowable<MutableList<Country>>
}