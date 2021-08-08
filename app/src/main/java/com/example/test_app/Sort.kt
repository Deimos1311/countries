package com.example.test_app

import com.example.test_app.dto.CountryDTO

class Sort {

    fun ascendingSort(listOfCountries: MutableList<CountryDTO>) {
        listOfCountries.sortBy { it.population }
    }

    fun descendingSort(listOfCountries: MutableList<CountryDTO>) {
        listOfCountries.sortByDescending { it.population }
    }
}
