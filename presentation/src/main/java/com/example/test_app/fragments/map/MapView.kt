package com.example.test_app.fragments.map

import com.example.test_app.base.mvp.BaseMvpView
import com.google.android.gms.maps.model.LatLng

interface MapView : BaseMvpView {

    fun showMap(location: LatLng)
}