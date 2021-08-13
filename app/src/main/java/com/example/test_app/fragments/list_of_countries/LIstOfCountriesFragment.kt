package com.example.test_app.fragments.list_of_countries

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.test_app.COUNTRY_FLAG_BUNDLE_KEY
import com.example.test_app.COUNTRY_NAME_BUNDLE_KEY
import com.example.test_app.R
import com.example.test_app.base.mvvm.Outcome
import com.example.test_app.databinding.FragmentListOfCountriesBinding
import com.example.test_app.dto.CountryDTO
import com.example.test_app.ext.createDialog
import com.example.test_app.room.DatabaseToRecyclerAdapter
import com.example.test_app.room.entity.CountryEntity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class LIstOfCountriesFragment : Fragment() {
    var searchSubject = BehaviorSubject.create<String>()
    private var binding: FragmentListOfCountriesBinding? = null

    lateinit var snackbar: Snackbar

    var isSorted: Boolean = false

    var searchList: MutableList<CountryDTO> = mutableListOf()
    var tempList: MutableList<CountryDTO> = mutableListOf()

    lateinit var listOfCountriesAdapter: ListOfCountriesAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var dataBaseToRecyclerAdapter: DatabaseToRecyclerAdapter

    var compositeDis = CompositeDisposable()


    private var viewModel: ListOfCountriesViewModel = ListOfCountriesViewModel(SavedStateHandle())

    var liveD: LiveData<MutableList<CountryEntity>>? = null
    var test: MutableList<CountryEntity> = mutableListOf()

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

        listOfCountriesAdapter = ListOfCountriesAdapter()
        dataBaseToRecyclerAdapter = DatabaseToRecyclerAdapter()

        //viewModel.getCountryByName()

        //daoCountry?.getAllCountries()
        /* liveD = daoCountry?.getAllCountries()
         liveD?.observe(viewLifecycleOwner, {
             dataBaseToRecyclerAdapter.addList(it)
         })*/

        viewModel.getListOfCountries()
        viewModel.listOfCountriesLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is Outcome.Progress -> {
                    if (binding?.swipeRefresh?.isRefreshing == false) {
                        binding?.progressBar?.isVisible = it.loading
                        binding?.frameWithProgress?.isVisible = it.loading
                    }
                }
                is Outcome.Success -> {
                }
                is Outcome.Next -> {
                    viewModel.addListOfCountriesToDatabase(it.data)
                    tempList = it.data

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
                    binding?.recyclerView?.adapter = dataBaseToRecyclerAdapter

                    binding?.swipeRefresh?.isRefreshing = false
                }
            }
        })

        binding?.swipeRefresh?.setOnRefreshListener {
            listOfCountriesAdapter.clear()
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

        instantSearch()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchSubject.onNext(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchSubject.onNext(newText)
                return true
            }
        })
    }

    fun instantSearch() {
        searchSubject
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { query ->
                searchList.clear()

                if (query.lowercase().isNotEmpty()) {
                    tempList.forEach {
                        if (it.countryName.lowercase().contains(query)) {
                            searchList.add(it)
                        }
                    }
                    listOfCountriesAdapter.clear()
                    listOfCountriesAdapter.addList(searchList)
                } else {
                    listOfCountriesAdapter.clear()
                    searchList.addAll(tempList)
                    listOfCountriesAdapter.addList(searchList)
                }
            }
        /*.observeOn(Schedulers.io())
        .filter { it.length >= MIN_SEARCH_STRING_LENGTH }
        .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
        .distinctUntilChanged()
        .flatMap {
            Common.retrofitService?.getCountryByName(it)?.toObservable()
                ?.onErrorResumeNext { Observable.just(mutableListOf()) }
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { text ->
            Log.d(TAG, "subscriber: $text")
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sort) {
            if (!isSorted) {
                item.setIcon(R.drawable.baseline_expand_more_24)
            } else {
                item.setIcon(R.drawable.baseline_expand_less_24)
            }

            listOfCountriesAdapter.isSorted(isSorted)
            //saveSortStatus()
            isSorted = !isSorted

        } else if (item.itemId == R.id.map_toolbar) {
            findNavController().navigate(
                R.id.action_list_of_countries_to_mapFragment
            )
        }

        /* if (sharedPreferences.contains("SortStatus")) {
             Log.d("MYAAPP", "DATAISHERE")
         }*/

        return super.onOptionsItemSelected(item)
    }

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
