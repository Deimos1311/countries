package com.example.test_app.fragments.list_of_countries

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.test_app.COUNTRY_FLAG_BUNDLE_KEY
import com.example.test_app.COUNTRY_NAME_BUNDLE_KEY
import com.example.test_app.R
import com.example.test_app.base.mvvm.Outcome
import com.example.test_app.databinding.FragmentListOfCountriesBinding
import com.example.domain.dto.CountryDTO
import com.example.test_app.ext.createDialog
import com.example.test_app.room.DatabaseToRecyclerAdapter
import com.example.data.room.entity.CountryEntity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class LIstOfCountriesFragment : ScopeFragment() {
    //var searchSubject = BehaviorSubject.create<String>()
    private var binding: FragmentListOfCountriesBinding? = null

    lateinit var snackbar: Snackbar

    var isSorted: Boolean = false

    //search country by name
    var searchListByName: MutableList<CountryDTO> = mutableListOf()
    var temporalList: MutableList<CountryDTO> = mutableListOf()

    //adapter
    lateinit var listOfCountriesAdapter: ListOfCountriesAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var dataBaseToRecyclerAdapter: DatabaseToRecyclerAdapter

    var compositeDis = CompositeDisposable()

    private val viewModel:ListOfCountriesViewModel by stateViewModel()

    var liveD: LiveData<MutableList<CountryEntity>>? = null
    var test: MutableList<CountryEntity> = mutableListOf()

    var populationFromSlider: MutableList<Float> = mutableListOf()

    var sortedList: MutableList<CountryDTO> = mutableListOf()

    //lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListOfCountriesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        //getSortStatus()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)

        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<MutableList<Float>>("Population")
            ?.observe(viewLifecycleOwner) { population ->
                populationFromSlider = population
                viewModel.searchByPopulation(temporalList, populationFromSlider, sortedList)

                viewModel.listof.observe(viewLifecycleOwner, {

                    when (it) {
                        is Outcome.Progress -> {}
                        is Outcome.Next -> {
                            listOfCountriesAdapter.clear()
                            Toast.makeText(requireContext(), "$population", Toast.LENGTH_SHORT).show()


                            listOfCountriesAdapter.addList(it.data)
                        }
                        is Outcome.Success -> {Toast.makeText(requireContext(), "$population", Toast.LENGTH_SHORT).show()

                            listOfCountriesAdapter.addList(it.data)}
                        is Outcome.Failure -> {}
                    }
                })




            }

/*        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<MutableList<Float>>("Area")
            ?.observe(viewLifecycleOwner) { area ->
                Toast.makeText(requireContext(), "$area", Toast.LENGTH_SHORT).show()
            }*/

        listOfCountriesAdapter = ListOfCountriesAdapter()
        dataBaseToRecyclerAdapter = DatabaseToRecyclerAdapter()

        viewModel.getListOfCountries()
        viewModel.listOfCountriesLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Outcome.Progress -> {

                    if (binding?.swipeRefresh?.isRefreshing == false) {
                        binding?.progressBar?.isVisible = it.loading
                        binding?.frameWithProgress?.isVisible = it.loading
                    }

                }
                is Outcome.Success -> {}
                is Outcome.Next -> {

                    listOfCountriesAdapter.clear()

                    temporalList = it.data
                    viewModel.instantSearch(searchListByName, temporalList)

                    snackbar = Snackbar.make(
                        requireView(),
                        resources.getString(R.string.done_message),
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.show()

                    listOfCountriesAdapter.addList(it.data)

                    binding?.recyclerView?.adapter = listOfCountriesAdapter
                    binding?.swipeRefresh?.isRefreshing = false

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

        viewModel.listOfCountriesGetFromDBLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Outcome.Progress -> {}
                is Outcome.Next -> {

                    dataBaseToRecyclerAdapter.addList(it.data)
                    binding?.recyclerView?.adapter = dataBaseToRecyclerAdapter

                }
                is Outcome.Success -> {}
                is Outcome.Failure -> {}
            }
        })

        //todo debug adding data to db when refresh without crash listOfCountriesFragment
        viewModel.addListOfCountriesToDB()
        viewModel.listOfCountriesAddToDBLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Outcome.Progress -> {}
                is Outcome.Next -> {}
                is Outcome.Success -> {}
                is Outcome.Failure -> {}
            }
        })

        binding?.swipeRefresh?.setOnRefreshListener {
            viewModel.getListOfCountries()
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
    }

    private fun initRecyclerView(view: View) {
        binding?.recyclerView?.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(activity)
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

                listOfCountriesAdapter.isSorted(isSorted)
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

        /* if (sharedPreferences.contains("SortStatus")) {
             Log.d("MYAAPP", "DATAISHERE")
         }*/

        return super.onOptionsItemSelected(item)
    }

    //todo sharedprefs
    /**not working yet*/
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
}
