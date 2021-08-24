package com.example.domain.usecase.impl.database

import com.example.domain.dto.CountryDTO
import com.example.domain.repository.DatabaseRepository
import com.example.domain.usecase.UseCaseUnit

class AddAllCountriesToDBUseCase(private val databaseRepository: DatabaseRepository) :
    UseCaseUnit<MutableList<CountryDTO>, Unit>() {

    override fun buildUnit(params: MutableList<CountryDTO>?) =
        databaseRepository.addAllCountries(params ?: mutableListOf())

    override val isParamsRequired: Boolean
        get() = true
}
