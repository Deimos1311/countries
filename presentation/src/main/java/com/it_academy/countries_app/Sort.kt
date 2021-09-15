package com.it_academy.countries_app

import com.it_academy.domain.dto.countries.CountryDTO

class Sort {

    fun ascendingSort(listOfCountries: MutableList<CountryDTO>) {
        listOfCountries.sortBy { it.population }
    }

    fun descendingSort(listOfCountries: MutableList<CountryDTO>) {
        listOfCountries.sortByDescending { it.population }
    }
}
