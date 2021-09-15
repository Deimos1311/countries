package com.it_academy.data.repository.coroutineRepo

import com.it_academy.data.models.countriesAPI.RegionModel
import com.it_academy.data.network.coroutine_service.CoroutineService
import com.it_academy.domain.dto.countries.RegionDTO
import com.it_academy.domain.repository.CoroutineRepository

class CoroutineRepositoryImpl(
    private val service: CoroutineService,
    private val regionModelToRegionDtoTransformer: com.it_academy.data.flow_transformer.Transformer<MutableList<RegionModel>, MutableList<RegionDTO>>
) : CoroutineRepository {
    override suspend fun getRegions(): MutableList<RegionDTO> =
        regionModelToRegionDtoTransformer.convert(service.getRegion())
}