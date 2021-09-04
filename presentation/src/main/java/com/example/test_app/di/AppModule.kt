package com.example.test_app.di

import com.example.data.interactor.CountryInteractorImpl
import com.example.data.network.common.Common
import com.example.data.repository.cache.CacheRepositoryImpl
import com.example.data.repository.database.DatabaseRepositoryImpl
import com.example.data.repository.networkRepo.NetworkRepositoryImpl
import com.example.data.room.CountryDatabase
import com.example.domain.interactor.CountryInteractor
import com.example.domain.repository.CacheRepository
import com.example.domain.repository.DatabaseRepository
import com.example.domain.repository.NetworkRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    //Model level
    single { CountryDatabase.getInstance(get()) }
    single { Common.retrofitService }

    //Data level
    single() {
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
}