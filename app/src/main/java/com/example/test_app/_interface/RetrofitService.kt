package com.example.test_app._interface

import com.example.test_app.model.Country
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

    @GET("rest/v2/all")
    fun getCountryDate(): Call<MutableList<Country>>
}