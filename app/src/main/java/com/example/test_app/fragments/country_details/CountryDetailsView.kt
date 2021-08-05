package com.example.test_app.fragments.country_details

import com.example.test_app.base.mvp.BaseMvpView
import com.example.test_app.model.Country
import com.google.android.gms.maps.model.LatLng

interface CountryDetailsView : BaseMvpView {

    fun showCountryInfo(country: Country, location: LatLng)

    fun internetError()
}