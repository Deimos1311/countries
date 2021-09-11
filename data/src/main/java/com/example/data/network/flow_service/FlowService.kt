package com.example.data.network.flow_service

import com.example.data.model.RegionModel
import com.example.data.network.NetConstants
import com.example.domain.dto.RegionDTO
import com.example.domain.outcome.Outcome
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface FlowService {

    @GET(NetConstants.GET_REGION)
    fun getRegion(): Flow<MutableList<RegionModel>>
}