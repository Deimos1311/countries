package com.example.test_app.di

import com.example.data.network.common.Common
import com.example.data.repository.MAIN_RETROFIT_SERVICE
import com.example.data.repository.database.DatabaseRepositoryImpl
import com.example.domain.repository.DatabaseRepository
import com.example.domain.repository.NetworkRepository
import com.example.data.repository.networkRepo.NetworkRepositoryImpl
import com.example.data.room.CountryDatabase
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    //Model level
    single { CountryDatabase.getInstance(get()) }
    single { Common.retrofitService }

    //Data level
    single(named(MAIN_RETROFIT_SERVICE)) {
        NetworkRepositoryImpl(get())
    } bind NetworkRepository::class

    single {
        DatabaseRepositoryImpl(get())
    } bind DatabaseRepository::class
}