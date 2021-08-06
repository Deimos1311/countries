package com.example.test_app.fragments.list_of_countries

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.test_app.*
import com.example.test_app.base.mvp.BaseMvpFragment
import com.example.test_app.common.Common
import com.example.test_app.databinding.FragmentListOfCountriesBinding
import com.example.test_app.dto.CountryDTO
import com.example.test_app.ext.createDialog
import com.example.test_app.model.Country
import com.example.test_app.room.CountryDAO
import com.example.test_app.room.CountryDatabase
import com.example.test_app.room.DatabaseToRecyclerAdapter
import com.example.test_app.room.entity.CountryEntity
import com.example.test_app.room.entity.CountryLanguageCrossRef
import com.example.test_app.room.entity.LanguagesListEntity
import com.example.test_app.transformers.transform
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class LIstOfCountriesFragment : BaseMvpFragment<ListOfCountriesView>(), ListOfCountriesView {
    var searchSubject = BehaviorSubject.create<String>()
    private var binding: FragmentListOfCountriesBinding? = null

    lateinit var snackbar: Snackbar

    var isSorted: Boolean = false

    var searchList: MutableList<CountryDTO> = mutableListOf()
    var tempList: MutableList<CountryDTO> = mutableListOf()

    lateinit var listOfCountriesAdapter: ListOfCountriesAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var dataBaseToRecyclerAdapter: DatabaseToRecyclerAdapter

    var countryDataBase: CountryDatabase? = null
    var daoCountry: CountryDAO? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListOfCountriesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPresenter().attachView(this)
        initRecyclerView(view)

        getPresenter().getListOfCountries()
        getPresenter().addListOfCountriesToDatabase()

        listOfCountriesAdapter = ListOfCountriesAdapter()

        binding?.swipeRefresh?.setOnRefreshListener {
            listOfCountriesAdapter.clear()
            getPresenter().getListOfCountries()
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

        countryDataBase = context?.let { CountryDatabase.getInstance(it) }
        daoCountry = countryDataBase?.countryDAO

        binding?.progressBar?.visibility = View.VISIBLE

        dataBaseToRecyclerAdapter = DatabaseToRecyclerAdapter()
        daoCountry?.getAllCountries()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({ list ->
                dataBaseToRecyclerAdapter.addList(list)
            }, { throwable ->
                throwable.printStackTrace()
            })
        //readingSortedListCountries()
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
                searchSubject.onNext(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchSubject.onNext(newText)
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
            .observeOn(Schedulers.io())
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
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sort) {
            if (!isSorted) {
                item.setIcon(R.drawable.baseline_expand_more_24)
            } else {
                item.setIcon(R.drawable.baseline_expand_less_24)
            }

            listOfCountriesAdapter.isSorted(isSorted)
            //savingSortedListCountries()
            isSorted = !isSorted

        } else if (item.itemId == R.id.map_toolbar) {
            findNavController().navigate(
                R.id.action_list_of_countries_to_mapFragment
            )
        }

        return super.onOptionsItemSelected(item)
    }

    /*fun savingSortedListCountries() {
        val sharedPref = activity?.getSharedPreferences("SAVE_SORT", Context.MODE_PRIVATE)
            ?.edit()
            ?.putBoolean(getString(R.string.isChecked), isSorted)
            ?.apply()
    }

    fun readingSortedListCountries() {
        val sharedPref = activity?.getSharedPreferences("SAVE_SORT", Context.MODE_PRIVATE)
        val ret = sharedPref?.getBoolean(getString(R.string.isChecked), isSorted)
        if (ret != null) {
            isSorted = ret
        }
    }*/

    override fun onPause() {
        super.onPause()
        //savingSortedListCountries()
    }

    override fun onDestroyView() {
        //compositeDisposable.clear()
        if (createDialog(requireActivity()).isShowing) createDialog(requireActivity()).dismiss()
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        //savingSortedListCountries()
    }

    override fun createPresenter() {
        mPresenter = ListOfCountriesPresenter()
    }

    override fun getPresenter(): ListOfCountriesPresenter = mPresenter as ListOfCountriesPresenter

    override fun populateDatabases(
        listOfCountryEntities: MutableList<CountryEntity>,
        listOfLanguagesEntities: MutableList<LanguagesListEntity>,
        crossRef: MutableList<CountryLanguageCrossRef>
    ) {
        daoCountry?.addAllCountries(listOfCountryEntities)
        daoCountry?.insertCountryLanguageCrossRef(crossRef)
        daoCountry?.addLanguage(listOfLanguagesEntities)
    }

    @SuppressLint("ShowToast")
    override fun showListOfCountries(listOfCountries: MutableList<CountryDTO>) {
        snackbar = Snackbar.make(
            requireView(),
            resources.getString(R.string.done_message),
            Snackbar.LENGTH_SHORT
        )

        snackbar.show()

        listOfCountriesAdapter.addList(listOfCountries)
        binding?.recyclerView?.adapter = listOfCountriesAdapter

        tempList = listOfCountries

        binding?.progressBar?.visibility = View.GONE
        binding?.frameWithProgress?.visibility = View.GONE
        binding?.swipeRefresh?.isRefreshing = false
    }

    @SuppressLint("ShowToast")
    override fun showError(error: String) {
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

        binding?.progressBar?.visibility = View.GONE
        binding?.frameWithProgress?.visibility = View.GONE
        binding?.swipeRefresh?.isRefreshing = false
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }
}
