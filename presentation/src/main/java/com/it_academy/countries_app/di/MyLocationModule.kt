package com.it_academy.countries_app.di

import com.it_academy.countries_app.fragments.my_location_map.MyLocationFragment
import com.it_academy.countries_app.fragments.my_location_map.MyLocationViewModel
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