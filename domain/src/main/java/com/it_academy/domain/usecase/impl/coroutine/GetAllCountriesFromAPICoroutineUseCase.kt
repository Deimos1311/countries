package com.it_academy.domain.usecase.impl.coroutine

import com.it_academy.domain.dto.countries.RegionDTO
import com.it_academy.domain.repository.CoroutineRepository
import com.it_academy.domain.usecase.UseCaseCoroutine

class GetAllRegionsFromAPICoroutineUseCase(private val coroutineRepository: CoroutineRepository) :
    UseCaseCoroutine<Unit, MutableList<RegionDTO>>() {

    override suspend fun buildCoroutine(params: Unit?): MutableList<RegionDTO> =
        coroutineRepository.getRegions()

    override val isParamsRequired: Boolean
        get() = false
}