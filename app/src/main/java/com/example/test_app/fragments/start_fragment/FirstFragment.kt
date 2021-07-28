package com.example.test_app.fragments.start_fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.test_app.COUNTRY_FLAG_BUNDLE_KEY
import com.example.test_app.COUNTRY_NAME_BUNDLE_KEY
import com.example.test_app.R
import com.example.test_app.common.Common
import com.example.test_app.ext.createDialog
import com.example.test_app.ext.showDialogWithOneButton
import com.example.test_app.model.Country
import com.example.test_app.room.CountryDAO
import com.example.test_app.room.CountryDatabase
import com.example.test_app.room.DatabaseToRecyclerAdapter
import com.example.test_app.room.entity.CountryEntity
import com.example.test_app.room.entity.CountryLanguageCrossRef
import com.example.test_app.room.entity.LanguagesListEntity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class FirstFragment : Fragment() {
    var isSorted: Boolean = false

    val listOfCountryEntities: MutableList<CountryEntity> = mutableListOf()
    val listOfLanguagesEntities: MutableList<LanguagesListEntity> = mutableListOf()
    val crossRef: MutableList<CountryLanguageCrossRef> = mutableListOf()

    var list: MutableList<Country> = mutableListOf()
    lateinit var tempList: MutableList<Country>

    lateinit var recyclerView: RecyclerView
    lateinit var customCountryAdapter: CustomCountryAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var dataBaseToRecyclerAdapter: DatabaseToRecyclerAdapter

    var countryDataBase: CountryDatabase? = null
    var daoCountry: CountryDAO? = null

    lateinit var srFirstFragment: SwipeRefreshLayout
    lateinit var progressBar: ProgressBar

    lateinit var flFirstFragment: FrameLayout

    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)

        progressBar = view.findViewById(R.id.progress_first_fragment)
        srFirstFragment = view.findViewById(R.id.sr_first_fragment)
        flFirstFragment = view.findViewById(R.id.frame_first_fragment)

        countryDataBase = context?.let { CountryDatabase.getInstance(it) }
        daoCountry = countryDataBase?.countryDAO
        dataBaseToRecyclerAdapter = DatabaseToRecyclerAdapter()
        daoCountry?.getAllCountries()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({ list ->
                dataBaseToRecyclerAdapter.addList(list)
            }, { throwable ->
                throwable.printStackTrace()
            })

        recyclerView.adapter = dataBaseToRecyclerAdapter

        customCountryAdapter = CustomCountryAdapter()
        customCountryAdapter.setItemClick { item ->
            val bundle = Bundle()
            bundle.putString(COUNTRY_NAME_BUNDLE_KEY, item.countryName)
            bundle.putString(COUNTRY_FLAG_BUNDLE_KEY, item.flag)
            findNavController().navigate(
                R.id.action_firstFragment_to_countryDetailsFragment,
                bundle
            )
        }

        srFirstFragment.setOnRefreshListener {
            customCountryAdapter.clear()
            getCountries(daoCountry, true)
        }

        getCountries(daoCountry, false)
        readingSortedListCountries()
        activity?.showDialogWithOneButton(
            getString(R.string.first_fragment_dialog_title),
            getString(R.string.first_fragment_dialog_description),
            R.string.button_dialog_with_one_button, null
        )
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.rView)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        val decoration = DividerItemDecoration(context, VERTICAL)
        recyclerView.addItemDecoration(decoration)
    }

    private fun getCountries(daoCountry: CountryDAO?, isRefreshing: Boolean) {
        progressBar.visibility = if (isRefreshing) View.GONE else View.VISIBLE

        val goodSnack: Snackbar = Snackbar.make(
            requireView(), resources.getString(R.string.done_message), Snackbar.LENGTH_SHORT
        )
        val badSnack: Snackbar = Snackbar.make(
            requireView(), resources.getText(R.string.connect_error_message), Snackbar.LENGTH_LONG
        )
        badSnack.setAction(R.string.bad_snack_info) {
            Toast.makeText(context, getString(R.string.bad_snack_inside), Toast.LENGTH_SHORT)
                .show()
        }

        val subscription = Common.retrofitService?.getCountryDate()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({ response ->
                response.forEach {
                    listOfCountryEntities.add(
                        CountryEntity(
                            it.countryName,
                            it.cityName,
                            it.population
                        )
                    )
                    (it.languages.forEach { item ->
                        listOfLanguagesEntities.add(
                            LanguagesListEntity(
                                item.iso639_1 ?: "",
                                item.iso639_2 ?: "",
                                item.name ?: "",
                                item.nativeName ?: ""
                            )
                        )
                    })
                }
                response.forEach {
                    crossRef.add(
                        CountryLanguageCrossRef(
                            it.countryName,
                            it.languages.joinToString { item ->
                                item.name ?: ""
                            }
                        )
                    )
                }

                daoCountry?.insertCountryLanguageCrossRef(crossRef)
                daoCountry?.addLanguage(listOfLanguagesEntities)
                daoCountry?.addAllCountries(listOfCountryEntities)


                srFirstFragment.isRefreshing = false

                customCountryAdapter.addList(response)
                tempList = response
                recyclerView.adapter = customCountryAdapter
                goodSnack.show()

                progressBar.visibility = View.GONE
                flFirstFragment.visibility = View.GONE
            }, { throwable ->
                throwable.printStackTrace()

                badSnack.show()
                getCountries(daoCountry, true)
                srFirstFragment.isRefreshing = false

                progressBar.visibility = View.GONE
                flFirstFragment.visibility = View.GONE
            })
        compositeDisposable.add(subscription)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        initRecyclerView(view)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar, menu)

        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.queryHint = "Search country"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit: $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "onQueryTextChange: $newText")

                val searchText = newText?.lowercase(Locale.getDefault())

                list.clear()

                if (searchText!!.isNotEmpty()) {
                    tempList.forEach {
                        if (it.countryName.lowercase(Locale.getDefault()).contains(searchText)) {
                            list.add(it)
                        }
                    }
                    customCountryAdapter.clear()
                    customCountryAdapter.addList(list)
                } else {
                    customCountryAdapter.clear()
                    list.addAll(tempList)
                    customCountryAdapter.addList(list)
                }

                return false
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!isSorted) {
            item.setIcon(R.drawable.baseline_expand_more_24)
        } else {
            item.setIcon(R.drawable.baseline_expand_less_24)
        }
        customCountryAdapter.isSorted(isSorted)
        savingSortedListCountries()
        isSorted = !isSorted
        return super.onOptionsItemSelected(item)
    }

    fun savingSortedListCountries() {
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
    }

    override fun onPause() {
        super.onPause()
        savingSortedListCountries()
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        if (createDialog(requireActivity()).isShowing) createDialog(requireActivity()).dismiss()
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        savingSortedListCountries()
    }
}
