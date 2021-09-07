package com.example.domain.usecase.impl.coroutine

import com.example.domain.dto.CapitalDTO
import com.example.domain.repository.CoroutineRepository
import com.example.domain.usecase.UseCaseCoroutine

class GetAllCapitalsFromAPICoroutineUseCase(private val coroutineRepository: CoroutineRepository) :
    UseCaseCoroutine<Unit, MutableList<CapitalDTO>>() {

    override suspend fun buildCoroutine(params: Unit?): MutableList<CapitalDTO> =
        coroutineRepository.getCapitals()

    override val isParamsRequired: Boolean
        get() = false

}