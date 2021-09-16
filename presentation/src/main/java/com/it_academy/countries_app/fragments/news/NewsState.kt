package com.it_academy.countries_app.fragments.news

import com.it_academy.countries_app.base.mvi.ViewState
import com.it_academy.domain.dto.news.SourceDTO

sealed class NewsState : ViewState {
    data class Loading(val loading: Boolean) : NewsState()
    data class ResultAllNews(val data: MutableList<SourceDTO>) : NewsState()
    data class Exception(val ex: Throwable) : NewsState()
}