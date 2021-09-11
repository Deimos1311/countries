package com.example.data.repository.flowRepo

import com.example.data.flow_trasformer.Transformer
import com.example.data.flow_trasformer.modifyFlow
import com.example.data.model.RegionModel
import com.example.data.network.flow_service.FlowService
import com.example.domain.dto.RegionDTO
import com.example.domain.outcome.Outcome
import com.example.domain.repository.FlowRepository
import kotlinx.coroutines.flow.Flow

class FlowRepositoryImpl(
    private val flowService: FlowService,
    private val regionModelToRegionDtoTransformer: Transformer<MutableList<RegionModel>, MutableList<RegionDTO>>
) : FlowRepository {

    override fun getRegion(): Flow<Outcome<MutableList<RegionDTO>>> =
        modifyFlow(flowService.getRegion(), regionModelToRegionDtoTransformer)
}