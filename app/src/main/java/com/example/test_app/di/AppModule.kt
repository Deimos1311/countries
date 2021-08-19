package com.example.test_app.di

import com.example.test_app.common.Common
import com.example.test_app.repository.MAIN_RETROFIT_SERVICE
import com.example.test_app.repository.database.DatabaseRepository
import com.example.test_app.repository.database.DatabaseRepositoryImpl
import com.example.test_app.repository.networkRepo.NetworkRepository
import com.example.test_app.repository.networkRepo.NetworkRepositoryImpl
import com.example.test_app.room.CountryDatabase
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
    single(named("key")) {
        DatabaseRepositoryImpl(get())
    } bind DatabaseRepository::class
}