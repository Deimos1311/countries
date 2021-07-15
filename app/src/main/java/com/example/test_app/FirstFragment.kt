package com.example.test_app

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.example.test_app.adapter.CustomCountryAdapter
import com.example.test_app.common.Common
import com.example.test_app.model.Country
import com.example.test_app.room.CountryDAO
import com.example.test_app.room.CountryDatabase
import com.example.test_app.room.entity.CountryEntity
import com.example.test_app.room.entity.CountryLanguageCrossRef
import com.example.test_app.room.entity.LanguagesListEntity
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Response

class FirstFragment : Fragment() {
    var listOfCountries: MutableList<Country> = mutableListOf()

    val listOfCountryEntities: MutableList<CountryEntity> = mutableListOf()
    val listOfLanguagesEntities: MutableList<LanguagesListEntity> = mutableListOf()
    val crossRef: MutableList<CountryLanguageCrossRef> = mutableListOf()

    lateinit var recyclerView: RecyclerView
    lateinit var customCountryAdapter: CustomCountryAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    var countryDataBase: CountryDatabase? = null
    var daoCountry: CountryDAO? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)

        //daoCountry = context?.let { CountryDatabase.getInstance(it).countryDAO }
        countryDataBase = context?.let { CountryDatabase.getInstance(it) }
        daoCountry = countryDataBase?.countryDAO

        getCountries(daoCountry)
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.rView)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = linearLayoutManager

        val decoration = DividerItemDecoration(context, VERTICAL)
        recyclerView.addItemDecoration(decoration)
    }

    private fun getCountries(daoCountry: CountryDAO?) {
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

/*
                crossRef.forEach { daoCountry?.insertCountryLanguageCrossRef(it) }
*/

                listOfCountries.forEach {
                    crossRef.add(
                        CountryLanguageCrossRef(
                            it.countryName,
                            it.languages.joinToString {item ->
                                item.name
                            }
                        )
                    )
                }
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
                                item.iso639_1 ?: "-1",
                                item.iso639_2,
                                item.name,
                                item.nativeName
                            )
                        )
                    })
                }

                countryDataBase?.clearAllTables()

/*                daoCountry?.clearPrimaryKey()*/
/*                lifecycleScope.launch {
                    *//*listOfCountryEntities.forEach { _ -> daoCountry?.addAllCountries(listOfCountryEntities) }
                    listOfLanguagesEntities.forEach { _ -> daoCountry?.addLanguage(listOfLanguagesEntities) }*//*
                    crossRef.forEach { daoCountry?.insertCountryLanguageCrossRef(it) }
                }*/

                daoCountry?.insertCountryLanguageCrossRef(crossRef)
                daoCountry?.addLanguage(listOfLanguagesEntities)
                daoCountry?.addAllCountries(listOfCountryEntities)


                customCountryAdapter = CustomCountryAdapter()
                customCountryAdapter.addList(listOfCountries)
                recyclerView.adapter = customCountryAdapter
                goodSnack.show()
            }

            override fun onFailure(call: Call<MutableList<Country>>, t: Throwable) {
                badSnack.show()
                getCountries(daoCountry)
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
        customCountryAdapter.isSorted(item)
        return super.onOptionsItemSelected(item)
    }
}

