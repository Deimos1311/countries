package com.example.test_app.fragments.my_location_map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.test_app.base.mvp.BaseMvpFragment
import com.example.test_app.databinding.FragmentMyLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
//TODO need to be finalized
class MyLocationFragment : BaseMvpFragment<MyLocationView>(), MyLocationView {
    var binding: FragmentMyLocationBinding? = null

    lateinit var googleMap: GoogleMap
    var currentLocation: Location? = null
    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        fetchLocation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyLocationBinding.inflate(inflater, container, false)

        binding?.myLocationMap?.onCreate(savedInstanceState)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter().attachView(this)
    }

    override fun onResume() {
        binding?.myLocationMap?.onResume()
        super.onResume()
    }

    override fun onLowMemory() {
        binding?.myLocationMap?.onLowMemory()
        super.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        binding?.myLocationMap?.onDestroy()
        super.onDestroy()
    }

    private fun fetchLocation() {
        if (ActivityCompat
                .checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            != PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat
                .checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
            return
        }
        val task = fusedLocationProviderClient?.lastLocation
        task?.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location

                binding?.myLocationMap?.getMapAsync(OnMapReadyCallback {
                    googleMap = it

                    getMyLocation()
                })
            }
        }
    }

    fun getMyLocation() {
        val latLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
        val markerPosition = MarkerOptions().position(latLng).title("I am here")

        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap.addMarker(markerPosition)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    fetchLocation()
            }
        }
    }

    override fun createPresenter() {
        mPresenter = MyLocationPresenter()
    }

    override fun getPresenter(): MyLocationPresenter = mPresenter as MyLocationPresenter

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }
}