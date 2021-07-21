package com.example.test_app._interface

import com.example.test_app.model.Country
import com.example.test_app.NetConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET(NetConstants.GET_COUNTRY_DATE)
    fun getCountryDate(): Call<MutableList<Country>>

    @GET(NetConstants.GET_COUNTRY_BY_NAME)
    fun getCountryByName(@Path(NetConstants.PATH_VARIABLE) name: String): Call<MutableList<Country>>
}