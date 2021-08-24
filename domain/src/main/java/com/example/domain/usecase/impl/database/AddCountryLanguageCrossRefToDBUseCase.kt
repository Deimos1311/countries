package com.example.domain.usecase.impl.database

import com.example.domain.dto.CountryLanguageCrossRefDTO
import com.example.domain.repository.DatabaseRepository
import com.example.domain.usecase.UseCaseUnit

class AddCountryLanguageCrossRefToDBUseCase(private val databaseRepository: DatabaseRepository) :
    UseCaseUnit<MutableList<CountryLanguageCrossRefDTO>, Unit>() {

    override fun buildUnit(params: MutableList<CountryLanguageCrossRefDTO>?) =
        databaseRepository.insertCountryLanguageCrossRef(params ?: mutableListOf())

    override val isParamsRequired: Boolean
        get() = true
}