package com.it_academy.data.network.flow_service.news

import com.it_academy.data.models.newsAPI.NewsModel
import com.it_academy.data.models.newsAPI.SourceModel
import com.it_academy.data.network.NewsNetConstants
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsFlowService {

    @Headers("X-Api-Key: 24cfb3468dae49a89f02a23a36de3202")
    @GET(NewsNetConstants.ALL_NEWS)
    fun getAllNews(): Flow<NewsModel>

    /*@GET(NewsNetConstants.NEWS_BY_COUNTRY)
    fun getNewsByCountry(@Path(NewsNetConstants.))*/
}