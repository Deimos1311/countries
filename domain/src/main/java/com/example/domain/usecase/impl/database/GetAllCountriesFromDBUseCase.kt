package com.example.domain.usecase.impl.database

import com.example.domain.dto.CountryDTO
import com.example.domain.repository.DatabaseRepository
import com.example.domain.usecase.UseCase
import io.reactivex.rxjava3.core.Flowable

class GetAllCountriesFromDBUseCase(private val databaseRepository: DatabaseRepository) :
    UseCase<Unit, MutableList<CountryDTO>>() {

    override fun buildFlowable(params: Unit?): Flowable<MutableList<CountryDTO>> =
        databaseRepository.getAllCountries()

    override val isParamsRequired: Boolean
        get() = false
}