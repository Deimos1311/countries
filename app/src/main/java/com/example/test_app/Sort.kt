package com.example.test_app

import com.example.test_app.model.Country

class Sort {

    fun ascendingSort(listOfCountries: MutableList<Country>) {
        listOfCountries.sortBy { it.population }
    }

    fun descendingSort(listOfCountries: MutableList<Country>) {
        listOfCountries.sortByDescending { it.population }
    }
}
