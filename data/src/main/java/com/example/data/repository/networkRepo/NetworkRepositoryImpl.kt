package com.example.data.repository.networkRepo

import com.example.data.network.retrofit_service.RetrofitService
import com.example.data.transformers.transformCountryModelMutableListToDto
import com.example.domain.dto.CountryDTO
import com.example.domain.repository.NetworkRepository
import io.reactivex.rxjava3.core.Flowable

class NetworkRepositoryImpl(private val service: RetrofitService) :
    NetworkRepository {

    override fun getCountryDate(): Flowable<MutableList<CountryDTO>> =
        service.getCountryDate().map { it.transformCountryModelMutableListToDto() }

    override fun getCountryByName(name: String): Flowable<MutableList<CountryDTO>> =
        service.getCountryByName(name).map { it.transformCountryModelMutableListToDto() }
}