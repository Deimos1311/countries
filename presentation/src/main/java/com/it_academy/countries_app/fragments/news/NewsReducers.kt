package com.it_academy.countries_app.fragments.news

import com.it_academy.domain.dto.news.SourceDTO
import com.it_academy.domain.outcome.Outcome

fun Outcome<MutableList<SourceDTO>>.reduce(): NewsState {
    return when (this) {
        is Outcome.Progress -> NewsState.Loading(loading)
        is Outcome.Success -> NewsState.ResultAllNews(data)
        is Outcome.Failure -> NewsState.Exception(exception)
        else -> NewsState.Exception(Throwable("data in Outcome.Next"))
    }
}