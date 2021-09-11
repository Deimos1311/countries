package com.example.test_app.di

import com.example.data.flow_trasformer.RegionModelToRegionDtoTransformer
import com.example.data.flow_trasformer.Transformer
import com.example.data.interactor.CountryInteractorImpl
import com.example.data.model.RegionModel
import com.example.data.network.common.Common
import com.example.data.repository.cacheRepo.CacheRepositoryImpl
import com.example.data.repository.coroutineRepo.CoroutineRepositoryImpl
import com.example.data.repository.databaseRepo.DatabaseRepositoryImpl
import com.example.data.repository.flowRepo.FlowRepositoryImpl
import com.example.data.repository.networkRepo.NetworkRepositoryImpl
import com.example.data.room.CountryDatabase
import com.example.domain.dto.RegionDTO
import com.example.domain.interactor.CountryInteractor
import com.example.domain.repository.*
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    //Model level
    single { CountryDatabase.getInstance(get()) }
    single { Common.retrofitService }
    single { Common.coroutineService }
    single { Common.flowService }

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
        CoroutineRepositoryImpl(get())
    } bind CoroutineRepository::class

    single {
        FlowRepositoryImpl(get(),get())
    } bind FlowRepository::class

    single {
        RegionModelToRegionDtoTransformer()
    } bind Transformer::class
}