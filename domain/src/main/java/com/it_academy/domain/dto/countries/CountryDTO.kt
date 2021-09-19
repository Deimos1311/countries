package com.it_academy.domain.dto.countries

import com.it_academy.domain.DOUBLE_NOT_AVAILABLE
import com.it_academy.domain.INT_NOT_AVAILABLE
import com.it_academy.domain.STRING_NOT_AVAILABLE

data class CountryDTO(
    var countryName: String = STRING_NOT_AVAILABLE,
    var cityName: String = STRING_NOT_AVAILABLE,
    var population: Int = INT_NOT_AVAILABLE,
    var languages: MutableList<LanguageDTO> = mutableListOf(),
    var flag: String = STRING_NOT_AVAILABLE,
    var area: Double = DOUBLE_NOT_AVAILABLE,
    var location: MutableList<Double> = mutableListOf(),
    var distance: Int = INT_NOT_AVAILABLE,
    var text: Int = INT_NOT_AVAILABLE
)
