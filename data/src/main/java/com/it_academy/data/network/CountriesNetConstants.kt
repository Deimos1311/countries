package com.it_academy.data.network

object CountriesNetConstants {
    const val PATH_VARIABLE = "path"
    private const val PATH_URL = "/{$PATH_VARIABLE}"

    const val COUNTRIES_API_BASE_URL = "https://restcountries.eu/rest/v2/"

    const val GET_COUNTRY_DATE = "all"
    const val GET_CAPITAL = "all?fields=capital"
    const val GET_REGION = "all?fields=region"
    const val GET_COUNTRY_BY_NAME = "name$PATH_URL"
    const val GET_CAPITAL_BY_NAME = "capital$PATH_URL"
}