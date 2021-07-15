package com.example.test_app

import android.view.MenuItem
import com.example.test_app.model.Country

var flag = false

class Sort {

    private fun ascendingSort(
        listOfCountries: MutableList<Country>,
        item: MenuItem
    ) {
        listOfCountries.sortBy { it.population }
        item.setIcon(R.drawable.baseline_expand_less_24)
    }

    private fun descendingSort(
        listOfCountries: MutableList<Country>,
        item: MenuItem
    ) {
        listOfCountries.sortByDescending { it.population }
        item.setIcon(R.drawable.baseline_expand_more_24)
    }

    fun sorting(
        listOfCountries: MutableList<Country>,
        item: MenuItem
    ) {
        flag = if (flag) {
            ascendingSort(listOfCountries, item)
            false
        } else {
            descendingSort(listOfCountries, item)
            true
        }
    }
}