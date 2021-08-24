package com.example.data.network.common

import com.example.data.network.NetConstants
import com.example.data.network.retrofit_service.RetrofitService
import com.example.data.network.retrofit_client.RetrofitClient

object Common {

    val retrofitService: RetrofitService?
        get() = RetrofitClient.getClient(NetConstants.SERVER_API_BASE_URL)?.create(RetrofitService::class.java)
}
