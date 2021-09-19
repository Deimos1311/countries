package com.it_academy.countries_app.fragments.start_screen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.it_academy.countries_app.R
import com.it_academy.countries_app.base.mvp.BaseMvpFragment
import com.it_academy.countries_app.databinding.FragmentStartScreenBinding
import com.it_academy.countries_app.services.LocationService
import com.it_academy.domain.LOCATION_ACTION
import javax.inject.Singleton

class StartScreenFragment : BaseMvpFragment<StartScreenView>(), StartScreenView {

    private var binding: FragmentStartScreenBinding? = null

    private val locationBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null && intent.action != null) {
                when (intent.action) {
                    LOCATION_ACTION -> {
                        Log.e(
                            "location",
                            intent.getParcelableExtra<Location>("location").toString()
                        )
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intentFilter = IntentFilter().apply { addAction(LOCATION_ACTION) }
        context?.registerReceiver(locationBroadcastReceiver, intentFilter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartScreenBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter().attachView(this)

        binding?.buttonToListOfCountries?.setOnClickListener {
            findNavController().navigate(R.id.action_start_screen_to_first_fragment)
        }
        binding?.buttonToMap?.setOnClickListener {
            findNavController().navigate(R.id.action_start_screen_to_mapFragment)
        }
        binding?.buttonToMyLocationMap?.setOnClickListener {
            findNavController().navigate(R.id.action_start_screen_to_myLocation)
        }
        binding?.buttonToCapitals?.setOnClickListener {
            findNavController().navigate(R.id.action_start_screen_to_listOfCapitalsFragment)
        }
        binding?.buttonToRegion?.setOnClickListener {
            findNavController().navigate(R.id.action_start_screen_to_regionFragment)
        }
        binding?.buttonToNews?.setOnClickListener {
            findNavController().navigate(R.id.action_start_screen_to_newsFragmentMvi)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.context?.startForegroundService(
                Intent(
                    this.context,
                    LocationService::class.java
                )
            )
        } else {
            this.context?.startService(
                Intent(
                    this.context,
                    LocationService::class.java
                )
            )
        }


    }

    override fun createPresenter() {
        mPresenter = StartScreenPresenter()
    }

    override fun getPresenter(): StartScreenPresenter = mPresenter as StartScreenPresenter

    override fun showError(error: String) {
        TODO("Not yet implemented")
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        context?.unregisterReceiver(locationBroadcastReceiver)
        super.onDestroy()
    }
}
