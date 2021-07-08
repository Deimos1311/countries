package com.example.test_app.common

import com.example.test_app._interface.RetrofitService
import com.example.test_app.retrofit.RetrofitClient

object Common {
    private val BASE_URL = "https://restcountries.eu/"

    val retrofitService: RetrofitService?
        get() = RetrofitClient.getClient(BASE_URL)?.create(RetrofitService::class.java)
}
