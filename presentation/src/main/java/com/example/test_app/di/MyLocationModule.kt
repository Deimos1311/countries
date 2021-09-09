package com.example.test_app.di

import com.example.test_app.fragments.my_location_map.MyLocationFragment
import com.example.test_app.fragments.my_location_map.MyLocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myLocationModule = module {

    scope<MyLocationFragment> {

        viewModel {
            MyLocationViewModel(
                get()
            )
        }
    }
}