package com.example.test_app

import com.example.test_app.model.Country

var flag = false

class Sort {

    fun ascendingSort(listOfCountries: MutableList<Country>) {
        listOfCountries.sortBy { it.population }
    }

    fun descendingSort(listOfCountries: MutableList<Country>) {
        listOfCountries.sortByDescending { it.population }
    }

    fun sorting(listOfCountries: MutableList<Country>) {
        flag = if (flag) {
            ascendingSort(listOfCountries)
            false
        } else {
            descendingSort(listOfCountries)
            true
        }
    }
}
