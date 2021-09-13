package com.example.data.repository.flowRepo

import com.example.data.flow_transformer.Transformer
import com.example.data.flow_transformer.modifyFlow
import com.example.data.model.CapitalModel
import com.example.data.network.flow_service.FlowService
import com.example.domain.DEBOUNCE_TIME
import com.example.domain.dto.CapitalDTO
import com.example.domain.outcome.Outcome
import com.example.domain.repository.FlowRepository
import kotlinx.coroutines.flow.*

class FlowRepositoryImpl(
    private val flowService: FlowService,
    private val capitalModelToCapitalDtoTransformer: Transformer<MutableList<CapitalModel>, MutableList<CapitalDTO>>
) : FlowRepository {

    override fun getCapitals(): Flow<Outcome<MutableList<CapitalDTO>>> =
        modifyFlow(flowService.getCapital(), capitalModelToCapitalDtoTransformer)

    override fun getCapitalByName(name: String): Flow<Outcome<MutableList<CapitalDTO>>> =
        modifyFlow(flowService.getCapitalByName(name), capitalModelToCapitalDtoTransformer)

}

