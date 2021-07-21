package com.example.test_app.fragments.country_details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.test_app.*
import com.example.test_app.common.Common
import com.example.test_app.model.Country
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Response

class CountryDetailsFragment : Fragment() {

    private lateinit var srCountryDetails: SwipeRefreshLayout
    private lateinit var ivFlag: ImageView
    private lateinit var tvCountryName: AppCompatTextView
    private lateinit var countryName: String
    private lateinit var flag: String
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    private var listOfCountries: MutableList<Country> = mutableListOf()
    lateinit var countryDetailsFragmentAdapter: CountryDetailsFragmentAdapter

    lateinit var recyclerView: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_country_details, container, false)

        countryName = arguments?.getString(COUNTRY_NAME_BUNDLE_KEY, DEFAULT_VALUE) ?: ERROR
        flag = arguments?.getString(COUNTRY_FLAG_BUNDLE_KEY, DEFAULT_VALUE) ?: ERROR


        mapView = view.findViewById(R.id.map_country_details)
        mapView.onCreate(savedInstanceState)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)

        tvCountryName = view.findViewById(R.id.country_name_details)
        ivFlag = view.findViewById(R.id.flag_details)
        srCountryDetails = view.findViewById(R.id.sr_country_details)
        tvCountryName.text = countryName

        val url = flag
        GlideToVectorYou
            .init()
            .with(this.context)
            .setPlaceHolder(
                R.drawable.ic_launcher_foreground,
                R.drawable.twotone_error_black_18
            )
            .load(Uri.parse(url), ivFlag)

        srCountryDetails.setOnRefreshListener {
            getCountryByName()
        }

        mapView.getMapAsync(OnMapReadyCallback {
            googleMap = it
        })

        getCountryByName()
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.rView_country_details)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager
    }

    private fun getCountryByName() {
        val countryLanguages = Common.retrofitService?.getCountryByName(countryName)

        countryLanguages?.enqueue(object : retrofit2.Callback<MutableList<Country>> {
            override fun onResponse(
                call: Call<MutableList<Country>>,
                response: Response<MutableList<Country>>
            ) {
                listOfCountries = response.body() ?: mutableListOf()
                countryDetailsFragmentAdapter = CountryDetailsFragmentAdapter()
                countryDetailsFragmentAdapter.addList(listOfCountries[0].languages)
                recyclerView.adapter = countryDetailsFragmentAdapter

                srCountryDetails.isRefreshing = false

                val position1 = LatLng(
                    listOfCountries[0].location[0],
                    listOfCountries[0].location[1]
                )

                googleMap.addMarker(
                    MarkerOptions().position(
                        position1
                    )
                )
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLng(position1)
                )

                //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position1, 10f))
            }

            override fun onFailure(call: Call<MutableList<Country>>, t: Throwable) {
                Toast.makeText(context, "somth wrong", Toast.LENGTH_SHORT).show()
                srCountryDetails.isRefreshing = false
            }
        })
    }
}