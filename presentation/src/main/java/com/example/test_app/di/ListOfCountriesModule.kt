package com.example.test_app.di

import com.example.domain.usecase.impl.network.GetAllCountriesFromAPIUseCase
import com.example.test_app.fragments.list_of_countries.ListOfCountriesFragment
import com.example.test_app.fragments.list_of_countries.ListOfCountriesViewModel
import com.example.domain.usecase.impl.database.*
import com.example.domain.usecase.impl.network.GetCountryListByNameFromAPIUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listOfCountriesModule = module {

    scope<ListOfCountriesFragment> {

        scoped { GetAllCountriesFromAPIUseCase(get()) }
        scoped { GetCountryListByNameFromAPIUseCase(get()) }
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
                get(),
                get()
            )
        }
    }
}
