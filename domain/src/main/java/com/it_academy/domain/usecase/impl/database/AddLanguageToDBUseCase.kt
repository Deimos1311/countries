package com.it_academy.domain.usecase.impl.database

import com.it_academy.domain.dto.countries.LanguageDTO
import com.it_academy.domain.repository.DatabaseRepository
import com.it_academy.domain.usecase.UseCaseUnit

class AddLanguageToDBUseCase(private val databaseRepository: DatabaseRepository) :
    UseCaseUnit<MutableList<LanguageDTO>, Unit>() {

    override fun buildUnit(params: MutableList<LanguageDTO>?) =
        databaseRepository.addLanguage(params ?: mutableListOf())

    override val isParamsRequired: Boolean
        get() = true
}