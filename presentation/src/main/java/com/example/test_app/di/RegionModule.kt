package com.example.test_app.di

import com.example.domain.usecase.impl.coroutine.GetAllRegionsFromAPICoroutineUseCase
import com.example.test_app.fragments.region.RegionFragment
import com.example.test_app.fragments.region.RegionViewModel
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