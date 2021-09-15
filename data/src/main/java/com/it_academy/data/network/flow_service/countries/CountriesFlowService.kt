package com.it_academy.data.network.flow_service.countries

import com.it_academy.data.models.countriesAPI.CapitalModel
import com.it_academy.data.network.CountriesNetConstants
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesFlowService {

    @GET(CountriesNetConstants.GET_CAPITAL)
    fun getCapital(): Flow<MutableList<CapitalModel>>

    @GET(CountriesNetConstants.GET_CAPITAL_BY_NAME)
    fun getCapitalByName(@Path(CountriesNetConstants.PATH_VARIABLE) name: String): Flow<MutableList<CapitalModel>>
}