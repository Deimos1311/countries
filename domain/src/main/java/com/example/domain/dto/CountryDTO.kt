package com.example.domain.dto

import com.example.domain.NULL_DOUBLE_VALUE
import com.example.domain.NULL_INT_VALUE
import com.example.domain.STRING_NULL_VALUE

data class CountryDTO(

    var countryName: String = STRING_NULL_VALUE,
    var cityName: String = STRING_NULL_VALUE,
    var population: Int = NULL_INT_VALUE,
    var languages: MutableList<LanguageDTO> = mutableListOf(),
    var flag: String = STRING_NULL_VALUE,
    var area: Double = NULL_DOUBLE_VALUE,
    var location: MutableList<Double> = mutableListOf(),
    var distance: Int = NULL_INT_VALUE,
    var text: Int = NULL_INT_VALUE
)
