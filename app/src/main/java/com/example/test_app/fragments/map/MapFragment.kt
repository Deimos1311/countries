package com.example.test_app.fragments.map

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.test_app.*
import com.example.test_app.base.mvp.BaseMvpFragment
import com.example.test_app.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : BaseMvpFragment<MapView>(), MapView {

    var binding: FragmentMapBinding? = null
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    lateinit var loc: LatLng

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        latitude = arguments?.getDouble(LATITUDE, DEFAULT_VALUE_DOUBLE) ?: ERROR_DOUBLE
        longitude = arguments?.getDouble(LONGITUDE, DEFAULT_VALUE_DOUBLE) ?: ERROR_DOUBLE

        binding?.map?.onCreate(savedInstanceState)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter().attachView(this)

        binding?.map?.getMapAsync(OnMapReadyCallback {
            googleMap = it
        })

        getPresenter().getMap()
    }

    override fun onResume() {
        binding?.map?.onResume()
        super.onResume()
    }

    override fun onLowMemory() {
        binding?.map?.onLowMemory()
        super.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        binding?.map?.onDestroy()
        super.onDestroy()
    }

    override fun createPresenter() {
        mPresenter = MapPresenter()
    }

    override fun getPresenter(): MapPresenter = mPresenter as MapPresenter

    override fun showMap(location: LatLng) {
        googleMap.addMarker(
            MarkerOptions().position(
                location
            )
        )

        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    latitude,
                    longitude
                ), CAMERA_ZOOM
            )
        )
    }

    override fun showError(error: String) {
        Log.d(TAG, "-------------------------------------------------------------------")
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }
}