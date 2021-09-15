package com.it_academy.data.network.common_countries

import com.it_academy.data.network.CountriesNetConstants
import com.it_academy.data.network.coroutine_service.CoroutineService
import com.it_academy.data.network.flow_service.countries.CountriesFlowService
import com.it_academy.data.network.retrofit_client.RetrofitClient
import com.it_academy.data.network.retrofit_service.RetrofitService

object CommonCountries {

    val retrofitService: RetrofitService?
        get() = RetrofitClient.getRetrofitClient(CountriesNetConstants.COUNTRIES_API_BASE_URL)
            ?.create(RetrofitService::class.java)

    val coroutineService: CoroutineService?
        get() = RetrofitClient.getCoroutineRetrofitClient(CountriesNetConstants.COUNTRIES_API_BASE_URL)
            ?.create(CoroutineService::class.java)

    val countriesFlowService: CountriesFlowService?
        get() = RetrofitClient.getFlowRetrofitClient(CountriesNetConstants.COUNTRIES_API_BASE_URL)
            ?.create(CountriesFlowService::class.java)
}
