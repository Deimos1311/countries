package com.example.test_app.di

import com.example.domain.usecase.impl.coroutine.GetAllCapitalsFromAPICoroutineUseCase
import com.example.test_app.fragments.list_of_capitals.ListOfCapitalsFragment
import com.example.test_app.fragments.list_of_capitals.ListOfCapitalsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listOfCapitalsModule = module {

    scope<ListOfCapitalsFragment> {

        scoped { GetAllCapitalsFromAPICoroutineUseCase(get()) }

        viewModel {
            ListOfCapitalsViewModel(
                get(),
                get()
            )
        }
    }
}