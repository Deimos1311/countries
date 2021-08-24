package com.example.domain.usecase.impl.network

import com.example.domain.dto.CountryDTO
import com.example.domain.repository.NetworkRepository
import com.example.domain.usecase.UseCase
import com.example.test_app.STRING_NULL_VALUE
import io.reactivex.rxjava3.core.Flowable

class GetCountryListByNameFromAPIUseCase(private val networkRepository: NetworkRepository) :
    UseCase<String, MutableList<CountryDTO>>() {

    override fun buildFlowable(params: String?): Flowable<MutableList<CountryDTO>> =
        networkRepository.getCountryByName(params ?: STRING_NULL_VALUE)

    override val isParamsRequired: Boolean
        get() = true
}