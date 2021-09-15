package com.it_academy.countries_app.di

import com.it_academy.countries_app.fragments.list_of_capitals.ListOfCapitalsFragment
import com.it_academy.countries_app.fragments.list_of_capitals.ListOfCapitalsViewModel
import com.it_academy.data.repository.flowRepo.CountriesFlowRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listOfCapitalsModule = module {

    scope<ListOfCapitalsFragment> {

        scoped { CountriesFlowRepositoryImpl(get(), get()) }

        viewModel {
            ListOfCapitalsViewModel(
                get(),
                get()
            )
        }
    }
}