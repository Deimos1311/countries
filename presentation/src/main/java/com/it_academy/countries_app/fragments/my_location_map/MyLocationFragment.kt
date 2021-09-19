package com.it_academy.countries_app.fragments.my_location_map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.it_academy.countries_app.R
import com.it_academy.countries_app.databinding.FragmentMyLocationBinding
import com.it_academy.domain.DEFAULT_LATITUDE
import com.it_academy.domain.DEFAULT_LONGITUDE
import com.it_academy.domain.MY_LOCATION_ZOOM
import com.it_academy.domain.REQUEST_CODE
import org.koin.androidx.scope.ScopeFragment

class MyLocationFragment : ScopeFragment() {

    private var binding: FragmentMyLocationBinding? = null

    private var googleMap: GoogleMap? = null
    private var currentLocation: Location? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            }
        )

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
        showProgress()
        return binding?.root
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
        googleMap = null
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

    private fun getMyLocation() {
        val latLng = LatLng(
            currentLocation?.latitude ?: DEFAULT_LATITUDE,
            currentLocation?.longitude ?: DEFAULT_LONGITUDE
        )
        val markerPosition = MarkerOptions().position(latLng).title(getString(R.string.i_am_here))

        googleMap?.uiSettings?.isZoomControlsEnabled = true
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, MY_LOCATION_ZOOM))
        googleMap?.addMarker(markerPosition)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    fetchLocation()
            }
        }
        hideProgress()
    }
    private fun showProgress() {
        binding?.frameWithProgress?.isVisible = true
        binding?.progressBar?.isVisible = true
    }

    private fun hideProgress() {
        binding?.frameWithProgress?.isVisible = false
        binding?.progressBar?.isVisible = false
    }
}
