package com.example.test_app.fragments.start_fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
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
import com.example.test_app.model.Country
import com.example.test_app.room.CountryDAO
import com.example.test_app.room.CountryDatabase
import com.example.test_app.room.DatabaseToRecyclerAdapter
import com.example.test_app.room.entity.CountryEntity
import com.example.test_app.room.entity.CountryLanguageCrossRef
import com.example.test_app.room.entity.LanguagesListEntity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Response

class FirstFragment : Fragment() {
    var isSorted: Boolean = false
    var listOfCountries: MutableList<Country> = mutableListOf()

    val listOfCountryEntities: MutableList<CountryEntity> = mutableListOf()
    val listOfLanguagesEntities: MutableList<LanguagesListEntity> = mutableListOf()
    val crossRef: MutableList<CountryLanguageCrossRef> = mutableListOf()

    lateinit var recyclerView: RecyclerView
    lateinit var customCountryAdapter: CustomCountryAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var dataBaseToRecyclerAdapter: DatabaseToRecyclerAdapter

    var countryDataBase: CountryDatabase? = null
    var daoCountry: CountryDAO? = null

    lateinit var srFirstFragment: SwipeRefreshLayout
    lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)

        progressBar = view.findViewById(R.id.progress_first_fragment)
        srFirstFragment = view.findViewById(R.id.sr_first_fragment)

        countryDataBase = context?.let { CountryDatabase.getInstance(it) }
        daoCountry = countryDataBase?.countryDAO

        dataBaseToRecyclerAdapter = DatabaseToRecyclerAdapter()
        daoCountry?.getAllCountries()?.let { dataBaseToRecyclerAdapter.addList(it) }
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
            customCountryAdapter.clear(listOfCountries)
            getCountries(daoCountry, true)
        }

        getCountries(daoCountry, false)
        readingSortedListCountries()
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

        val countryData = Common.retrofitService?.getCountryDate()

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

        countryData?.enqueue(object : retrofit2.Callback<MutableList<Country>> {
            override fun onResponse(
                call: Call<MutableList<Country>>,
                response: Response<MutableList<Country>>
            ) {
                listOfCountries = response.body() ?: mutableListOf()
                listOfCountries.forEach {
                    listOfCountryEntities.add(
                        CountryEntity(
                            it.countryName,
                            it.cityName,
                            it.population,
                            it.flag
                            //it.languages
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
                listOfCountries.forEach {
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

                customCountryAdapter.addList(listOfCountries)
                recyclerView.adapter = customCountryAdapter
                goodSnack.show()
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<MutableList<Country>>, t: Throwable) {
                badSnack.show()
                //getCountries(daoCountry, true)
                srFirstFragment.isRefreshing = false
                progressBar.visibility = View.GONE
            }
        })
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

    override fun onDestroy() {
        super.onDestroy()
        savingSortedListCountries()
    }
}
