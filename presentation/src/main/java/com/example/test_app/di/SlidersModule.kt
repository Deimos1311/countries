package com.example.test_app.di

import com.example.domain.usecase.impl.network.GetAllCountriesFromAPIUseCase
import com.example.test_app.fragments.sliders.SlidersFragment
import com.example.test_app.fragments.sliders.SlidersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val slidersModule = module {

    scope<SlidersFragment> {

        scoped { GetAllCountriesFromAPIUseCase(get()) }

        viewModel {
            SlidersViewModel(
                get(),
                get(),
            )
        }
    }
}