package com.example.test_app.repository.networkRepo

import com.example.test_app._interface.RetrofitService
import com.example.test_app.dto.CountryDTO
import com.example.test_app.transformers.transformToMutableListDto
import io.reactivex.rxjava3.core.Flowable
import org.koin.core.qualifier.named

class NetworkRepositoryImpl(private val service: RetrofitService) : NetworkRepository {
    override fun getCountryDate(): Flowable<MutableList<CountryDTO>> =
        service.getCountryDate().map { it.transformToMutableListDto() }


    override fun getCountryByName(name: String): Flowable<MutableList<CountryDTO>> =
        service.getCountryByName(name).map { it.transformToMutableListDto() }
}