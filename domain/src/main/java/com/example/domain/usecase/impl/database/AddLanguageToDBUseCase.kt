package com.example.domain.usecase.impl.database

import com.example.domain.dto.LanguageDTO
import com.example.domain.repository.DatabaseRepository
import com.example.domain.usecase.UseCaseUnit

class AddLanguageToDBUseCase(private val databaseRepository: DatabaseRepository) :
    UseCaseUnit<MutableList<LanguageDTO>, Unit>() {

    override fun buildUnit(params: MutableList<LanguageDTO>?) =
        databaseRepository.addLanguage(params ?: mutableListOf())

    override val isParamsRequired: Boolean
        get() = true
}