package com.it_academy.countries_app.di

import com.it_academy.countries_app.fragments.list_of_countries.ListOfCountriesFragment
import com.it_academy.countries_app.fragments.list_of_countries.ListOfCountriesViewModel
import com.it_academy.domain.usecase.impl.database.*
import com.it_academy.domain.usecase.impl.network.GetAllCountriesFromAPIUseCase
import com.it_academy.domain.usecase.impl.network.GetCountryListByNameFromAPIUseCase
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
