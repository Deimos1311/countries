package com.it_academy.domain.usecase.impl.database

import com.it_academy.domain.dto.countries.LanguageDTO
import com.it_academy.domain.repository.DatabaseRepository
import com.it_academy.domain.usecase.UseCase
import io.reactivex.rxjava3.core.Flowable

class GetLanguageFromDBUseCase(private val databaseRepository: DatabaseRepository) :
    UseCase<Unit, MutableList<LanguageDTO>>() {

    override fun buildFlowable(params: Unit?): Flowable<MutableList<LanguageDTO>> =
        databaseRepository.getLanguages()

    override val isParamsRequired: Boolean
        get() = false
}