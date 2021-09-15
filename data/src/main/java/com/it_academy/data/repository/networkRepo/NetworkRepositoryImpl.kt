package com.it_academy.data.repository.networkRepo

import com.it_academy.data.network.retrofit_service.RetrofitService
import com.it_academy.data.transformers.transformCountryModelMutableListToDto
import com.it_academy.domain.dto.countries.CountryDTO
import com.it_academy.domain.repository.NetworkRepository
import io.reactivex.rxjava3.core.Flowable

class NetworkRepositoryImpl(private val service: RetrofitService) :
    NetworkRepository {

    override fun getCountryDate(): Flowable<MutableList<CountryDTO>> =
        service.getCountryDate().map { it.transformCountryModelMutableListToDto() }

    override fun getCountryByName(name: String): Flowable<MutableList<CountryDTO>> =
        service.getCountryByName(name).map { it.transformCountryModelMutableListToDto() }
}