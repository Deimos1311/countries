package com.it_academy.domain.usecase.impl.network

import com.it_academy.domain.STRING_NOT_AVAILABLE
import com.it_academy.domain.dto.countries.CountryDTO
import com.it_academy.domain.repository.NetworkRepository
import com.it_academy.domain.usecase.UseCase
import io.reactivex.rxjava3.core.Flowable

class GetCountryListByNameFromAPIUseCase(private val networkRepository: NetworkRepository) :
    UseCase<String, MutableList<CountryDTO>>() {

    override fun buildFlowable(params: String?): Flowable<MutableList<CountryDTO>> =
        networkRepository.getCountryByName(params ?: STRING_NOT_AVAILABLE)

    override val isParamsRequired: Boolean
        get() = true
}