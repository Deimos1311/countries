package com.it_academy.data.repository.flowRepo

import com.it_academy.data.flow_transformer.Transformer
import com.it_academy.data.flow_transformer.modifyFlow
import com.it_academy.data.models.countriesAPI.CapitalModel
import com.it_academy.data.network.flow_service.countries.CapitalsFlowService
import com.it_academy.domain.DEBOUNCE_TIME
import com.it_academy.domain.dto.countries.CapitalDTO
import com.it_academy.domain.outcome.Outcome
import com.it_academy.domain.repository.CountriesFlowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class CountriesFlowRepositoryImpl(
    private val capitalsFlowService: CapitalsFlowService,
    private val capitalModelToCapitalDtoTransformer: Transformer<MutableList<CapitalModel>, MutableList<CapitalDTO>>,
) : CountriesFlowRepository {

    override fun getCapitals(): Flow<Outcome<MutableList<CapitalDTO>>> =
        modifyFlow(capitalsFlowService.getCapital(), capitalModelToCapitalDtoTransformer)

    override fun getCapitalByName(name: String): Flow<Outcome<MutableList<CapitalDTO>>> {
        val filterStateFlow = MutableStateFlow(name)
            .flatMapConcat { string ->
                capitalsFlowService.getCapitalByName(string)
            }
            .flowOn(Dispatchers.IO)
            .catch { emitAll(flowOf()) }

        return modifyFlow(filterStateFlow, capitalModelToCapitalDtoTransformer)
    }
}
