package com.it_academy.domain.usecase.impl.database

import com.it_academy.domain.dto.countries.CountryLanguageCrossRefDTO
import com.it_academy.domain.repository.DatabaseRepository
import com.it_academy.domain.usecase.UseCaseUnit

class AddCountryLanguageCrossRefToDBUseCase(private val databaseRepository: DatabaseRepository) :
    UseCaseUnit<MutableList<CountryLanguageCrossRefDTO>, Unit>() {

    override fun buildUnit(params: MutableList<CountryLanguageCrossRefDTO>?) =
        databaseRepository.insertCountryLanguageCrossRef(params ?: mutableListOf())

    override val isParamsRequired: Boolean
        get() = true
}