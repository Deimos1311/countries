package com.example.test_app.common

import com.example.test_app._interface.RetrofitService
import com.example.test_app.retrofit.RetrofitClient
import com.example.test_app.NetConstants

object Common {

    val retrofitService: RetrofitService?
        get() = RetrofitClient.getClient(NetConstants.SERVER_API_BASE_URL)?.create(RetrofitService::class.java)
}
