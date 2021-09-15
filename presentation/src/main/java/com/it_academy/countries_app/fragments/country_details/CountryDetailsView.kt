package com.it_academy.countries_app.fragments.country_details

import com.google.android.gms.maps.model.LatLng
import com.it_academy.countries_app.base.mvp.BaseMvpView
import com.it_academy.domain.dto.countries.CountryDTO

interface CountryDetailsView : BaseMvpView {

    fun showCountryInfo(country: CountryDTO, location: LatLng)

    fun internetError()
}