package com.example.test_app

import android.view.MenuItem
import com.example.test_app.adapter.CustomAdapter
import com.example.test_app.model.Country

var flag = false

class Sort {

    private fun ascendingSort(
        customAdapter: CustomAdapter,
        listOfCountries: MutableList<Country>?,
        item: MenuItem
    ) {
        listOfCountries?.sortBy { it.population }
        item.setIcon(R.drawable.baseline_expand_less_24)
        customAdapter.notifyDataSetChanged()
    }

    private fun descendingSort(
        customAdapter: CustomAdapter,
        listOfCountries: MutableList<Country>?,
        item: MenuItem
    ) {
        listOfCountries?.sortByDescending { it.population }
        item.setIcon(R.drawable.baseline_expand_more_24)
        customAdapter.notifyDataSetChanged()
    }

    fun sorting(
        customAdapter: CustomAdapter,
        listOfCountries: MutableList<Country>?,
        item: MenuItem
    ) {
        flag = if (flag) {
            ascendingSort(customAdapter, listOfCountries, item)
            false
        } else {
            descendingSort(customAdapter, listOfCountries, item)
            true
        }
    }
}