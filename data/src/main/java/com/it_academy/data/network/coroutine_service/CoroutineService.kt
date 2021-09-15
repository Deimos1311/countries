package com.it_academy.data.network.coroutine_service

import com.it_academy.data.models.countriesAPI.RegionModel
import com.it_academy.data.network.CountriesNetConstants
import retrofit2.http.GET

interface CoroutineService {

    @GET(CountriesNetConstants.GET_REGION)
    suspend fun getRegion(): MutableList<RegionModel>

}