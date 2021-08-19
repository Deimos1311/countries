package com.example.test_app.di

import com.example.test_app.fragments.list_of_countries.LIstOfCountriesFragment
import com.example.test_app.fragments.list_of_countries.ListOfCountriesViewModel
import com.example.test_app.repository.MAIN_RETROFIT_SERVICE
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val listOfCountriesModule = module {

    scope<LIstOfCountriesFragment> {

        viewModel { /*(handle: SavedStateHandle) ->*/
            ListOfCountriesViewModel(
                /*handle,*/
                get(),
                get(named(MAIN_RETROFIT_SERVICE)),
                get(named("key"))
            )
        }
    }
}