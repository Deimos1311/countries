package com.it_academy.domain.usecase.impl.database

import com.it_academy.domain.dto.countries.CountryDTO
import com.it_academy.domain.repository.DatabaseRepository
import com.it_academy.domain.usecase.UseCaseUnit

class AddAllCountriesToDBUseCase(private val databaseRepository: DatabaseRepository) :
    UseCaseUnit<MutableList<CountryDTO>, Unit>() {

    override fun buildUnit(params: MutableList<CountryDTO>?) =
        databaseRepository.addAllCountries(params ?: mutableListOf())

    override val isParamsRequired: Boolean
        get() = true
}
