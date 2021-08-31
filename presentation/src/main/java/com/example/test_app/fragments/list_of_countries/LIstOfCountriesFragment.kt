package com.example.test_app.fragments.list_of_countries

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.room.entity.CountryEntity
import com.example.domain.dto.CountryDTO
import com.example.test_app.COUNTRY_FLAG_BUNDLE_KEY
import com.example.test_app.COUNTRY_NAME_BUNDLE_KEY
import com.example.test_app.R
import com.example.test_app.SLIDERS_KEY
import com.example.test_app.base.mvvm.Outcome
import com.example.test_app.databinding.FragmentListOfCountriesBinding
import com.example.test_app.ext.createDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class LIstOfCountriesFragment : ScopeFragment() {

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    lateinit var myLocation: Location

    private var binding: FragmentListOfCountriesBinding? = null

    lateinit var snackbar: Snackbar

    var isSorted: Boolean = false

    //search country by name
    var searchListByName: MutableList<CountryDTO> = mutableListOf()
    var temporalList: MutableList<CountryDTO> = mutableListOf()

    //adapter
    lateinit var listOfCountriesAdapter: ListOfCountriesAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    private val viewModel: ListOfCountriesViewModel by stateViewModel()

    var test: MutableList<CountryEntity> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        viewModel.getListOfCountries()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListOfCountriesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        getMyLocation()


        viewModel.listOfCountriesLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Outcome.Progress -> {

                    if (binding?.swipeRefresh?.isRefreshing == false) {
                        binding?.progressBar?.isVisible = it.loading
                        binding?.frameWithProgress?.isVisible = it.loading
                    }

                }
                is Outcome.Next -> {


                    temporalList = it.data
                    viewModel.instantSearch(searchListByName, temporalList)

                    snackbar = Snackbar.make(
                        requireView(),
                        resources.getString(R.string.done_message),
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.show()

                    listOfCountriesAdapter.refresh(it.data)
                    Log.e("hz", "message data")

                    binding?.swipeRefresh?.isRefreshing = false

                }
                is Outcome.Success -> {
                    viewModel.addListOfCountriesToDB()
                }
                is Outcome.Failure -> {

                    viewModel.getListOfCountriesFromDB()

                    it.exception.printStackTrace()

                    snackbar = Snackbar.make(
                        requireView(),
                        resources.getText(R.string.connect_error_message),
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.show()
                    snackbar.setAction(R.string.bad_snack_info) {
                        Toast.makeText(
                            context,
                            getString(R.string.bad_snack_inside),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    binding?.swipeRefresh?.isRefreshing = false
                }
            }
        })

        //getSortStatus()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)

        listOfCountriesAdapter = ListOfCountriesAdapter()
        binding?.recyclerView?.adapter = listOfCountriesAdapter

        viewModel.sortedListLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Outcome.Progress -> {

                    if (binding?.swipeRefresh?.isRefreshing == false) {
                        binding?.progressBar?.isVisible = it.loading
                        binding?.frameWithProgress?.isVisible = it.loading
                    }

                }
                is Outcome.Next -> {

                    Toast.makeText(requireContext(), "Eeeend its gone", Toast.LENGTH_SHORT).show()

                    Log.e("hz", "${it.data}")
                    listOfCountriesAdapter.refresh(it.data)

                    binding?.swipeRefresh?.isRefreshing = false

                }
                is Outcome.Success -> {
                }
                is Outcome.Failure -> {
                    Log.e("hz", "message")
                    viewModel.getListOfCountriesFromDB()

                    it.exception.printStackTrace()

                    snackbar = Snackbar.make(
                        requireView(),
                        resources.getText(R.string.connect_error_message),
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.show()
                    snackbar.setAction(R.string.bad_snack_info) {
                        Toast.makeText(
                            context,
                            getString(R.string.bad_snack_inside),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    binding?.swipeRefresh?.isRefreshing = false
                }
            }
        })

        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<MutableList<Float>>(SLIDERS_KEY)
            ?.observe(viewLifecycleOwner) {
                viewModel.searchBySliderFragment(it[0], it[1], it[2], it[3], it[4])
            }

        viewModel.listOfCountriesAddToDBLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Outcome.Progress -> {
                }
                is Outcome.Next -> {
                }
                is Outcome.Success -> {
                }
                is Outcome.Failure -> {
                }
            }
        })

        viewModel.listOfCountriesGetFromDBLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Outcome.Progress -> {
                }
                is Outcome.Next -> {
                    listOfCountriesAdapter.refresh(it.data)
                }
                is Outcome.Success -> {
                }
                is Outcome.Failure -> {
                }
            }
        })

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

        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.queryHint = getString(R.string.search_by_country_name)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchSubject.onNext(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchSubject.onNext(newText)
                listOfCountriesAdapter.clear()
                listOfCountriesAdapter.addList(searchListByName)
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

    override fun onDestroyView() {
        if (createDialog(requireActivity()).isShowing) createDialog(requireActivity()).dismiss()
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener { location ->
            viewModel.attachCurrentLocation(location)
        }
    }
}
