package com.example.test_app.fragments.map

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.test_app.MainActivity
import com.example.test_app.R
import com.example.test_app.base.mvp.BaseMvpFragment
import com.example.test_app.databinding.FragmentMapBinding
import com.example.test_app.fragments.list_of_countries.LIstOfCountriesFragment
import com.example.test_app.fragments.start_screen.StartScreenFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : BaseMvpFragment<MapView>(), MapView {

    var binding: FragmentMapBinding? = null

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
        binding?.map?.onResume()
        super.onLowMemory()
    }

    override fun onDestroy() {
        binding?.map?.onDestroy()
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
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