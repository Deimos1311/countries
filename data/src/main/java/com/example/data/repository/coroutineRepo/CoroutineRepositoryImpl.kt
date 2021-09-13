package com.example.data.repository.coroutineRepo

import com.example.data.flow_transformer.Transformer
import com.example.data.model.RegionModel
import com.example.data.network.coroutine_service.CoroutineService
import com.example.domain.dto.RegionDTO
import com.example.domain.repository.CoroutineRepository

class CoroutineRepositoryImpl(
    private val service: CoroutineService,
    private val regionModelToRegionDtoTransformer: Transformer<MutableList<RegionModel>, MutableList<RegionDTO>>
) : CoroutineRepository {
    override suspend fun getRegions(): MutableList<RegionDTO> =
        regionModelToRegionDtoTransformer.convert(service.getRegion())
}