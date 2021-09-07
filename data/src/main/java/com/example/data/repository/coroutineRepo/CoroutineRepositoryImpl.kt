package com.example.data.repository.coroutineRepo

import com.example.data.network.coroutine_service.CoroutineService
import com.example.data.transformers.transformCountryModelMutableListToDto
import com.example.data.transformers.transformMutableListCapitalModelToDto
import com.example.domain.dto.CapitalDTO
import com.example.domain.dto.CountryDTO
import com.example.domain.repository.CoroutineRepository

class CoroutineRepositoryImpl(private val service: CoroutineService) : CoroutineRepository {
    override suspend fun getCapitals(): MutableList<CapitalDTO> =
        service.getCapitals().transformMutableListCapitalModelToDto()
}