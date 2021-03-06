package com.it_academy.countries_app.fragments.country_details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.it_academy.countries_app.R
import com.it_academy.countries_app.base.mvp.BaseMvpFragment
import com.it_academy.countries_app.databinding.FragmentCountryDetailsBinding
import com.it_academy.domain.*
import com.it_academy.domain.dto.countries.CountryDTO

class CountryDetailsFragment : BaseMvpFragment<CountryDetailsView>(), CountryDetailsView {

    var binding: FragmentCountryDetailsBinding? = null

    private lateinit var countryName: String
    private lateinit var flag: String
    private var googleMap: GoogleMap? = null

    lateinit var countryDetailsFragmentAdapter: CountryDetailsFragmentAdapter

    lateinit var linearLayoutManager: LinearLayoutManager

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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryDetailsBinding.inflate(inflater, container, false)

        countryName = arguments?.getString(COUNTRY_NAME_BUNDLE_KEY, DEFAULT_VALUE) ?: ERROR
        flag = arguments?.getString(COUNTRY_FLAG_BUNDLE_KEY, DEFAULT_VALUE) ?: ERROR

        binding?.mapView?.onCreate(savedInstanceState)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter().attachView(this)
        initRecyclerView(view)

        countryDetailsFragmentAdapter = CountryDetailsFragmentAdapter()
        binding?.recyclerView?.adapter = countryDetailsFragmentAdapter

        binding?.countryName?.text = countryName

        GlideToVectorYou
            .init()
            .with(this.context)
            .setPlaceHolder(
                R.drawable.ic_launcher_foreground,
                R.drawable.twotone_error_black_18
            )
            .load(Uri.parse(flag), binding?.flag)

        binding?.mapView?.getMapAsync(OnMapReadyCallback {
            googleMap = it
        })

        binding?.swipeRefresh?.setOnRefreshListener {
            getPresenter().getCountryByName(countryName, true)
        }
        getPresenter().getCountryByName(countryName, false)
    }

    private fun initRecyclerView(view: View) {
        binding?.recyclerView?.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(activity)
        binding?.recyclerView?.layoutManager = linearLayoutManager
    }

    override fun onResume() {
        binding?.mapView?.onResume()
        super.onResume()
    }

    override fun onLowMemory() {
        binding?.mapView?.onLowMemory()
        super.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        googleMap = null
        getPresenter().detachView()
    }

    override fun onDestroy() {
        binding?.mapView?.onDestroy()
        super.onDestroy()
    }

    override fun createPresenter() {
        mPresenter = CountryDetailsPresenter()
    }

    override fun getPresenter(): CountryDetailsPresenter = mPresenter as CountryDetailsPresenter

    override fun showCountryInfo(country: CountryDTO, location: LatLng) {
        countryDetailsFragmentAdapter.refresh(country.languages)

        binding?.swipeRefresh?.isRefreshing = false

        googleMap?.addMarker(
            MarkerOptions().position(
                location
            )
        )
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, CAMERA_ZOOM))
        googleMap?.setOnMapClickListener {
            val bundle = Bundle()
            bundle.putDouble(LATITUDE, location.latitude)
            bundle.putDouble(LONGITUDE, location.longitude)
            findNavController().navigate(
                R.id.action_country_details_fragment_to_mapFragment,
                bundle
            )
        }
        binding?.frameWithProgress?.visibility = View.GONE
    }

    override fun internetError() {
        showToastShort(R.string.somth_wrong)
        binding?.swipeRefresh?.isRefreshing = false
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