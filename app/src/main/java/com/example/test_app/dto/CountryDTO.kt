package com.example.test_app.dto

import com.example.test_app.NULL_DOUBLE_VALUE
import com.example.test_app.NULL_INT_VALUE
import com.example.test_app.NULL_STRING_VALUE
import com.example.test_app.model.Language

data class CountryDTO(

    var countryName: String = NULL_STRING_VALUE,
    var cityName: String = NULL_STRING_VALUE,
    var population: Int = NULL_INT_VALUE,
    var languages: MutableList<Language> = mutableListOf(),
    var flag: String = NULL_STRING_VALUE,
    var area: Double = NULL_DOUBLE_VALUE,
    var location: MutableList<Double> = mutableListOf()

)
