package com.example.data.network.common

import com.example.data.network.NetConstants
import com.example.data.network.coroutine_service.CoroutineService
import com.example.data.network.retrofit_client.RetrofitClient
import com.example.data.network.retrofit_service.RetrofitService

object Common {

    val retrofitService: RetrofitService?
        get() = RetrofitClient.getRetrofitClient(NetConstants.SERVER_API_BASE_URL)
            ?.create(RetrofitService::class.java)

    val coroutineService: CoroutineService?
        get() = RetrofitClient.getCoroutineRetrofitClient(NetConstants.SERVER_API_BASE_URL)
            ?.create(CoroutineService::class.java)
}
