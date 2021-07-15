package com.example.test_app._interface

import com.example.test_app.model.Country
import com.example.test_app.model.Languages
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

    @GET("rest/v2/all")
    fun getCountryDate(): Call<MutableList<Country>>

    @GET("rest/v2/lang/es")
    fun getLanguagesDate(): Call<MutableList<Languages>>
}