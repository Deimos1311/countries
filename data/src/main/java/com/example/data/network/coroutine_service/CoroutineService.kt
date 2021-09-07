package com.example.data.network.coroutine_service

import com.example.data.model.CapitalModel
import com.example.data.model.CountryModel
import com.example.data.network.NetConstants
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface CoroutineService {

    @GET(NetConstants.GET_CAPITAL)
    suspend fun getCapitals(): MutableList<CapitalModel>

}