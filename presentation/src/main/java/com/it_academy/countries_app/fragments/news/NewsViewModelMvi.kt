package com.it_academy.countries_app.fragments.news

import com.it_academy.countries_app.base.mvi.MviBaseViewModel
import com.it_academy.domain.repository.NewsFlowRepository
import kotlinx.coroutines.flow.collect

class NewsViewModelMvi(private val flowRepository: NewsFlowRepository) :
    MviBaseViewModel<NewsIntent, NewsAction, NewsState>() {
    override fun intentToAction(intent: NewsIntent): NewsAction {
        return when (intent) {
            is NewsIntent.LoadAllNews -> NewsAction.AllNews
        }
    }

    override fun handleAction(action: NewsAction) {
        launchOnUi {
            when (action) {
                is NewsAction.AllNews -> {
                    flowRepository.getAllNews().collect {
                        mState.postValue(it.reduce())
                    }
                }
            }
        }
    }
}