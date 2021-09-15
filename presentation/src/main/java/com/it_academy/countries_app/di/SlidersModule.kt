package com.it_academy.countries_app.di

import com.it_academy.countries_app.fragments.sliders.SlidersFragment
import com.it_academy.countries_app.fragments.sliders.SlidersViewModel
import com.it_academy.domain.usecase.impl.network.GetAllCountriesFromAPIUseCase
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