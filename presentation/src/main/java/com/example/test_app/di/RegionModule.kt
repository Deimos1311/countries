package com.example.test_app.di

import com.example.data.repository.flowRepo.FlowRepositoryImpl
import com.example.domain.repository.FlowRepository
import com.example.test_app.fragments.region.RegionFragment
import com.example.test_app.fragments.region.RegionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val regionModule = module {

    scope<RegionFragment> {

        scoped { FlowRepositoryImpl(get(), get()) }

        viewModel {
            RegionViewModel(
                get(),
                get()
            )
        }
    }
}