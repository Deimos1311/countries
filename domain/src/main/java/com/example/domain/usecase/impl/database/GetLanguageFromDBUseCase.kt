package com.example.domain.usecase.impl.database

import com.example.domain.dto.LanguageDTO
import com.example.domain.repository.DatabaseRepository
import com.example.domain.usecase.UseCase
import io.reactivex.rxjava3.core.Flowable

class GetLanguageFromDBUseCase(private val databaseRepository: DatabaseRepository) :
    UseCase<Unit, MutableList<LanguageDTO>>() {

    override fun buildFlowable(params: Unit?): Flowable<MutableList<LanguageDTO>> =
        databaseRepository.getLanguages()

    override val isParamsRequired: Boolean
        get() = false
}