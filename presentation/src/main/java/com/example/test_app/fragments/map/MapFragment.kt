package com.example.test_app.fragments.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.domain.*
import com.example.test_app.R
import com.example.test_app.base.mvp.BaseMvpFragment
import com.example.test_app.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

//todo refactoring
class MapFragment : BaseMvpFragment<MapView>(), MapView {

    private var binding: FragmentMapBinding? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        latitude = arguments?.getDouble(LATITUDE, DEFAULT_LATITUDE) ?: ERROR_DOUBLE
        longitude = arguments?.getDouble(LONGITUDE, DEFAULT_LONGITUDE) ?: ERROR_DOUBLE

        binding?.map?.onCreate(savedInstanceState)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter().attachView(this)

        binding?.map?.getMapAsync(OnMapReadyCallback {
            googleMap = it
        })
        getPresenter().getMap(true)
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
        googleMap = null
        getPresenter().detachView()
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
        googleMap?.addMarker(
            MarkerOptions().position(
                location
            )
        )

        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    latitude,
                    longitude
                ), CAMERA_ZOOM
            )
        )

        binding?.frameWithProgress?.visibility = View.GONE
    }

    override fun showError(error: String) {
        showToastShort(R.string.error)
        print(error)
    }

    override fun showProgress() {
        binding?.progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding?.progressBar?.visibility = View.GONE
    }

    private fun showToastShort(textId: Int) {
        Toast.makeText(requireContext(), textId, Toast.LENGTH_SHORT).show()
    }
}