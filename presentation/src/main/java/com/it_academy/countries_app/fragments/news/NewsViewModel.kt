package com.it_academy.countries_app.fragments.news

import androidx.lifecycle.SavedStateHandle
import com.it_academy.countries_app.base.mvvm.BaseViewModel
import com.it_academy.domain.dto.news.SourceDTO
import com.it_academy.domain.outcome.Outcome
import com.it_academy.domain.repository.NewsFlowRepository
import kotlinx.coroutines.flow.Flow

class NewsViewModel(
    savedStateHandle: SavedStateHandle,
    private val newsFlowRepository: NewsFlowRepository
) : BaseViewModel(savedStateHandle) {

    fun getAllNews(): Flow<Outcome<MutableList<SourceDTO>>> =
        newsFlowRepository.getAllNews()
}