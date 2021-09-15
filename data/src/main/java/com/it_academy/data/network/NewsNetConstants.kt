package com.it_academy.data.network

import retrofit2.http.Header

object NewsNetConstants {

    const val NEWS_API_BASE_URL = "https://newsapi.org"

    const val ALL_NEWS = "/v2/top-headlines/sources"
    const val NEWS_BY_COUNTRY = "/v2/top-headlines"
}