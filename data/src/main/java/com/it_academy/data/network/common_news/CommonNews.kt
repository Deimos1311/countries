package com.it_academy.data.network.common_news

import com.it_academy.data.network.NewsNetConstants
import com.it_academy.data.network.flow_service.news.NewsFlowService
import com.it_academy.data.network.retrofit_client.RetrofitClient

object CommonNews {

    val newsFlowService: NewsFlowService?
        get() = RetrofitClient.getFlowRetrofitClient(NewsNetConstants.NEWS_API_BASE_URL)
            ?.create(NewsFlowService::class.java)
}