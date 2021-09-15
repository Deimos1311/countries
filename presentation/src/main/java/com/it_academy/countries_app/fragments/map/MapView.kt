package com.it_academy.countries_app.fragments.map

import com.google.android.gms.maps.model.LatLng
import com.it_academy.countries_app.base.mvp.BaseMvpView

interface MapView : BaseMvpView {

    fun showMap(location: LatLng)
}