package com.it_academy.countries_app.di

import com.it_academy.countries_app.fragments.region.RegionFragment
import com.it_academy.countries_app.fragments.region.RegionViewModel
import com.it_academy.domain.usecase.impl.coroutine.GetAllRegionsFromAPICoroutineUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val regionModule = module {

    scope<RegionFragment> {

        scoped { GetAllRegionsFromAPICoroutineUseCase(get()) }

        viewModel {
            RegionViewModel(
                get(),
                get()
            )
        }
    }
}