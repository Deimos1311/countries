package com.example.test_app

object NetConstants {
    const val PATH_VARIABLE = "path"
    const val PATH_URL = "/{$PATH_VARIABLE}"

    const val SERVER_API_BASE_URL = "https://restcountries.eu/rest/v2/"

    const val GET_COUNTRY_DATE = "all"
    const val GET_COUNTRY_BY_NAME = "name$PATH_URL"
}