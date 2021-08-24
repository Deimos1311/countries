package com.example.test_app.di

import com.example.domain.usecase.impl.network.GetAllCountriesFromAPIUseCase
import com.example.test_app.fragments.list_of_countries.LIstOfCountriesFragment
import com.example.test_app.fragments.list_of_countries.ListOfCountriesViewModel
import com.example.data.repository.MAIN_RETROFIT_SERVICE
import com.example.domain.usecase.impl.database.*
import com.example.domain.usecase.impl.network.GetCountryListByNameFromAPIUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val listOfCountriesModule = module {

    scope<LIstOfCountriesFragment> {

        scoped { GetAllCountriesFromAPIUseCase(get(named(MAIN_RETROFIT_SERVICE))) }
        scoped { GetCountryListByNameFromAPIUseCase(get(named(MAIN_RETROFIT_SERVICE))) }
        scoped { GetAllCountriesFromDBUseCase(get()) }
        scoped { GetLanguageFromDBUseCase(get()) }
        scoped { AddAllCountriesToDBUseCase(get()) }
        scoped { AddLanguageToDBUseCase(get()) }
        scoped { AddCountryLanguageCrossRefToDBUseCase(get()) }

        viewModel {
            ListOfCountriesViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get()
            )
        }
    }
}
