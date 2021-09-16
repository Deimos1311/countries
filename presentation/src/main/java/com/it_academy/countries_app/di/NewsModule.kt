package com.it_academy.countries_app.di

import com.it_academy.countries_app.fragments.news.NewsFragmentMvi
import com.it_academy.countries_app.fragments.news.NewsViewModel
import com.it_academy.countries_app.fragments.news.NewsViewModelMvi
import com.it_academy.data.repository.flowRepo.NewsFlowRepositoryImpl
import com.it_academy.domain.repository.NewsFlowRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var newsModule = module {

    scope<NewsFragmentMvi> {

        scoped { NewsFlowRepositoryImpl(get(), get()) }

        viewModel {
            NewsViewModelMvi(
                get()
            )
        }
    }
}