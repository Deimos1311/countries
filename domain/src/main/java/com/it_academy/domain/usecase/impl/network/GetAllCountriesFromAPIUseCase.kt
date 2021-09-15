package com.it_academy.domain.usecase.impl.network

import com.it_academy.domain.dto.countries.CountryDTO
import com.it_academy.domain.repository.NetworkRepository
import com.it_academy.domain.usecase.UseCase
import io.reactivex.rxjava3.core.Flowable

class GetAllCountriesFromAPIUseCase(private val networkRepository: NetworkRepository) :
    UseCase<Any, MutableList<CountryDTO>>() {

    override fun buildFlowable(params: Any?): Flowable<MutableList<CountryDTO>> =
        networkRepository.getCountryDate()

    override val isParamsRequired: Boolean
        get() = false
}