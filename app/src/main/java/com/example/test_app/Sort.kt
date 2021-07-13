package com.example.test_app

import android.view.MenuItem
import com.example.test_app.adapter.CustomCountryAdapter
import com.example.test_app.model.Country

var flag = false

class Sort {

    private fun ascendingSort(
        customCountryAdapter: CustomCountryAdapter,
        listOfCountries: MutableList<Country>,
        item: MenuItem
    ) {
        listOfCountries.sortBy { it.population }
        item.setIcon(R.drawable.baseline_expand_less_24)
        customCountryAdapter.notifyDataSetChanged()
    }

    private fun descendingSort(
        customCountryAdapter: CustomCountryAdapter,
        listOfCountries: MutableList<Country>,
        item: MenuItem
    ) {
        listOfCountries.sortByDescending { it.population }
        item.setIcon(R.drawable.baseline_expand_more_24)
        customCountryAdapter.notifyDataSetChanged()
    }

    fun sorting(
        customCountryAdapter: CustomCountryAdapter,
        listOfCountries: MutableList<Country>,
        item: MenuItem
    ) {
        flag = if (flag) {
            ascendingSort(customCountryAdapter, listOfCountries, item)
            false
        } else {
            descendingSort(customCountryAdapter, listOfCountries, item)
            true
        }
    }
}