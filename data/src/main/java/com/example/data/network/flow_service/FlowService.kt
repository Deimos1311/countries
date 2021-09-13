package com.example.data.network.flow_service

import com.example.data.model.CapitalModel
import com.example.data.network.NetConstants
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface FlowService {

    @GET(NetConstants.GET_CAPITAL)
    fun getCapital(): Flow<MutableList<CapitalModel>>

    @GET(NetConstants.GET_CAPITAL_BY_NAME)
    fun getCapitalByName(@Path(NetConstants.PATH_VARIABLE) name: String): Flow<MutableList<CapitalModel>>
}