package com.it_academy.domain.usecase.impl.database

import com.it_academy.domain.dto.countries.CountryDTO
import com.it_academy.domain.repository.DatabaseRepository
import com.it_academy.domain.usecase.UseCase
import io.reactivex.rxjava3.core.Flowable

class GetAllCountriesFromDBUseCase(private val databaseRepository: DatabaseRepository) :
    UseCase<Unit, MutableList<CountryDTO>>() {

    override fun buildFlowable(params: Unit?): Flowable<MutableList<CountryDTO>> =
        databaseRepository.getAllCountries()

    override val isParamsRequired: Boolean
        get() = false
}