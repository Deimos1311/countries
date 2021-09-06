package com.example.test_app.fragments.list_of_countries

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.dto.CountryDTO
import com.example.test_app.*
import com.example.test_app.base.mvvm.Outcome
import com.example.test_app.databinding.FragmentListOfCountriesBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.stateViewModel
//todo maybe best practice to do all through one filter
class ListOfCountriesFragment : ScopeFragment() {

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var DEFAULT_LOCATION: Location = Location("")

    private var binding: FragmentListOfCountriesBinding? = null

    var isSorted: Boolean = false

    //search country by name
    var searchListByName: MutableList<CountryDTO> = mutableListOf()
    var temporalList: MutableList<CountryDTO> = mutableListOf()

    //adapter
    lateinit var listOfCountriesAdapter: ListOfCountriesAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    private val viewModel: ListOfCountriesViewModel by stateViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        viewModel.subscribeCountryChannel()
        viewModel.getListOfCountries()

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (listOfCountriesAdapter.size() < 249) {
                        viewModel.getListOfCountries()
                    } else {
                        findNavController().navigate(R.id.action_list_of_countries_to_start_screen)
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListOfCountriesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        getMyLocation()

        //getSortStatus()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)

        listOfCountriesAdapter = ListOfCountriesAdapter()
        binding?.recyclerView?.adapter = listOfCountriesAdapter

        viewModel.dataStatesLivaData.observe(viewLifecycleOwner, {
            when (it) {
                is Outcome.Progress -> {
                    if (it.loading) showProgress() else hideProgress()
                }
                is Outcome.Next -> {
                    binding?.swipeRefresh?.isRefreshing = false
                }
                is Outcome.Success -> {
                    showSnackbarShort(R.string.done_message)
                }
                is Outcome.Failure -> {
                    showSnackbarShortWithActionToast(
                        R.string.connect_error_message,
                        R.string.bad_snack_inside,
                        R.string.bad_snack_info
                    )
                    binding?.swipeRefresh?.isRefreshing = false
                }
            }
        })
// todo(maybe) when it sorting list comes 2 times
        viewModel.dataLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Outcome.Progress -> {
                    if (it.loading) showProgress() else hideProgress()
                }
                is Outcome.Next -> {

                    listOfCountriesAdapter.refresh(it.data)
                    hideProgress()
                }
                is Outcome.Success -> {
                    listOfCountriesAdapter.refresh(it.data)
                    hideProgress()
                }
                is Outcome.Failure -> {
                }
            }
        })

        setFragmentResultListener(SLIDERS_KEY) {_, bundle ->
            val result = bundle.getParcelableArrayList<Parcelable>(FILTER_KEY) as MutableList<Float>
            viewModel.searchBySliderFragment(result[0], result[1], result[2], result[3], result[4])
        }

        listOfCountriesAdapter.setItemClick { item ->
            val bundle = Bundle()
            bundle.putString(COUNTRY_NAME_BUNDLE_KEY, item.countryName)
            bundle.putString(COUNTRY_FLAG_BUNDLE_KEY, item.flag)
            findNavController().navigate(
                R.id.action_firstFragment_to_countryDetailsFragment,
                bundle
            )
        }

        binding?.swipeRefresh?.setOnRefreshListener {
            viewModel.getListOfCountries()
        }
    }

    private fun initRecyclerView(view: View) {

        linearLayoutManager = LinearLayoutManager(activity)
        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.layoutManager = linearLayoutManager

        val decoration = DividerItemDecoration(context, VERTICAL)
        binding?.recyclerView?.addItemDecoration(decoration)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar, menu)
        viewModel.instantSearch(searchListByName)

        val searchButton = menu.findItem(R.id.search).actionView as SearchView

        searchButton.queryHint = getString(R.string.search_by_country_name)

        searchButton.setOnSearchClickListener {
            menu.findItem(R.id.map_toolbar).isVisible = false
            menu.findItem(R.id.sort).isVisible = false
            menu.findItem(R.id.sliders_search).isVisible = false
        }

        searchButton.setOnCloseListener {
            menu.findItem(R.id.map_toolbar).isVisible = true
            menu.findItem(R.id.sort).isVisible = true
            menu.findItem(R.id.sliders_search).isVisible = true
            return@setOnCloseListener false
        }


        searchButton.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchSubject.onNext(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchSubject.onNext(newText)
                listOfCountriesAdapter.refresh(searchListByName)
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sort -> {
                if (!isSorted) {
                    item.setIcon(R.drawable.baseline_expand_more_24)
                } else {
                    item.setIcon(R.drawable.baseline_expand_less_24)
                }
                //listOfCountriesAdapter.isSorted(isSorted)
                //saveSortStatus()
                isSorted = !isSorted

            }
            R.id.map_toolbar -> {
                findNavController().navigate(
                    R.id.action_list_of_countries_to_mapFragment
                )
            }
            R.id.sliders_search -> {
                findNavController().navigate(
                    R.id.action_list_of_countries_to_slidersFragment
                )
            }
        }

        /*if (sharedPreferences.contains("SortStatus")) {
            Log.d("MYAAPP", "DATAISHERE")
        }*/

        return super.onOptionsItemSelected(item)
    }

    //todo sharedprefs
    //not working yet
    /*fun saveSortStatus() {
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("SortStatus", isSorted)
            apply()
        }
    }

    fun getSortStatus() {
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val value = sharedPreferences.getBoolean("SortStatus", false)
        isSorted = value
    }*/

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }
//todo transfer to dialogue and customize it
    /*override fun onDestroyView() {
        if (createDialog(requireActivity()).isShowing) createDialog(requireActivity()).dismiss()
        super.onDestroyView()
        binding = null
    }*/

    override fun onDestroy() {
        super.onDestroy()
    }

    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        DEFAULT_LOCATION.latitude = DEFAULT_LATITUDE
        DEFAULT_LOCATION.longitude = DEFAULT_LONGITUDE

        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener { location ->
            if (location != null) {
                viewModel.attachCurrentLocation(location)
            } else {
                viewModel.attachCurrentLocation(DEFAULT_LOCATION)
            }
        }
    }

    private fun showProgress() {
        //binding?.swipeRefresh?.isRefreshing = false
        binding?.progressBar?.isVisible = true
        binding?.frameWithProgress?.isVisible = true
    }

    private fun hideProgress() {
        //binding?.swipeRefresh?.isRefreshing = true
        binding?.progressBar?.isVisible = false
        binding?.frameWithProgress?.isVisible = false
    }

    private fun showToastShort(textId: Int) {
        Toast.makeText(requireContext(), textId, Toast.LENGTH_SHORT).show()
    }

    private fun showSnackbarShort(snackId: Int) {
        Snackbar.make(requireView(), snackId, Snackbar.LENGTH_SHORT).show()
    }

    private fun showSnackbarShortWithActionToast(snackId: Int, toastId: Int, actionId: Int) {
        Snackbar.make(requireView(), snackId, Snackbar.LENGTH_SHORT)
            .setAction(actionId) {
                showToastShort(toastId)
            }.show()

    }
}

