package com.example.domain.usecase.impl.network

import com.example.domain.dto.CountryDTO
import com.example.domain.repository.NetworkRepository
import com.example.domain.usecase.UseCase
import io.reactivex.rxjava3.core.Flowable

class GetAllCountriesFromAPIUseCase(private val networkRepository: NetworkRepository) :
    UseCase<Any, MutableList<CountryDTO>>() {

    override fun buildFlowable(params: Any?): Flowable<MutableList<CountryDTO>> =
        networkRepository.getCountryDate()

    override val isParamsRequired: Boolean
        get() = false
}