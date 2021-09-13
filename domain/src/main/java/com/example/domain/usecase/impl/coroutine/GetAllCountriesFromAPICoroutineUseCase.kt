package com.example.domain.usecase.impl.coroutine

import com.example.domain.dto.RegionDTO
import com.example.domain.repository.CoroutineRepository
import com.example.domain.usecase.UseCaseCoroutine

class GetAllRegionsFromAPICoroutineUseCase(private val coroutineRepository: CoroutineRepository) :
    UseCaseCoroutine<Unit, MutableList<RegionDTO>>() {

    override suspend fun buildCoroutine(params: Unit?): MutableList<RegionDTO> =
        coroutineRepository.getRegions()

    override val isParamsRequired: Boolean
        get() = false
}