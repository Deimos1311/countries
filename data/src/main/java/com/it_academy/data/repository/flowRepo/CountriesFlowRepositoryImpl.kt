package com.it_academy.data.repository.flowRepo

import com.it_academy.data.flow_transformer.Transformer
import com.it_academy.data.flow_transformer.modifyFlow
import com.it_academy.data.models.countriesAPI.CapitalModel
import com.it_academy.data.network.flow_service.countries.CountriesFlowService
import com.it_academy.domain.dto.countries.CapitalDTO
import com.it_academy.domain.outcome.Outcome
import com.it_academy.domain.repository.CountriesFlowRepository
import kotlinx.coroutines.flow.Flow

class CountriesFlowRepositoryImpl(
    private val countriesFlowService: CountriesFlowService,
    private val capitalModelToCapitalDtoTransformer: Transformer<MutableList<CapitalModel>, MutableList<CapitalDTO>>,
) : CountriesFlowRepository {

    override fun getCapitals(): Flow<Outcome<MutableList<CapitalDTO>>> =
        modifyFlow(countriesFlowService.getCapital(), capitalModelToCapitalDtoTransformer)

    override fun getCapitalByName(name: String): Flow<Outcome<MutableList<CapitalDTO>>> =
        modifyFlow(countriesFlowService.getCapitalByName(name), capitalModelToCapitalDtoTransformer)
}

