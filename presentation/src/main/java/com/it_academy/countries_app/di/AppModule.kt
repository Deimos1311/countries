package com.it_academy.countries_app.di

import com.it_academy.data.flow_transformer.Transformer
import com.it_academy.data.interactor.CountryInteractorImpl
import com.it_academy.data.network.common_countries.CommonCountries
import com.it_academy.data.network.common_news.CommonNews
import com.it_academy.data.repository.cacheRepo.CacheRepositoryImpl
import com.it_academy.data.repository.coroutineRepo.CoroutineRepositoryImpl
import com.it_academy.data.repository.databaseRepo.DatabaseRepositoryImpl
import com.it_academy.data.repository.flowRepo.CountriesFlowRepositoryImpl
import com.it_academy.data.repository.flowRepo.NewsFlowRepositoryImpl
import com.it_academy.data.repository.networkRepo.NetworkRepositoryImpl
import com.it_academy.data.room.CountryDatabase
import com.it_academy.data.transformers.CapitalModelToCapitalDtoTransformer
import com.it_academy.data.transformers.RegionModelToRegionDtoTransformer
import com.it_academy.data.transformers.SourceModelToSourceDtoTransformer
import com.it_academy.domain.interactor.CountryInteractor
import com.it_academy.domain.repository.*
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    //Model level
    single { CountryDatabase.getInstance(get()) }
    single { CommonCountries.retrofitService }
    single { CommonCountries.coroutineService }
    single { CommonCountries.capitalsFlowService }
    single { CommonNews.newsFlowService }

    //Data level
    single {
        NetworkRepositoryImpl(get())
    } bind NetworkRepository::class

    single {
        DatabaseRepositoryImpl(get())
    } bind DatabaseRepository::class

    single {
        CacheRepositoryImpl()
    } bind CacheRepository::class

    single {
        CountryInteractorImpl(get(), get(), get())
    } bind CountryInteractor::class

    single {
        CoroutineRepositoryImpl(get(), get(named("Region")))
    } bind CoroutineRepository::class

    single {
        CountriesFlowRepositoryImpl(get(), get(named("Capital")))
    } bind CountriesFlowRepository::class

    single {
        NewsFlowRepositoryImpl(get(), get(named("News")))
    } bind NewsFlowRepository::class

    single(named("Region")) {
        RegionModelToRegionDtoTransformer()
    } bind Transformer::class

    single(named("Capital")) {
        CapitalModelToCapitalDtoTransformer()
    } bind Transformer::class

    single(named("News")) {
        SourceModelToSourceDtoTransformer()
    } bind Transformer::class
}