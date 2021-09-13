package com.example.data.network.coroutine_service

import com.example.data.model.RegionModel
import com.example.data.network.NetConstants
import retrofit2.http.GET

interface CoroutineService {

    @GET(NetConstants.GET_REGION)
    suspend fun getRegion(): MutableList<RegionModel>

}