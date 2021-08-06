package com.example.test_app.transformers

import com.example.test_app.NULL_DOUBLE_VALUE
import com.example.test_app.NULL_INT_VALUE
import com.example.test_app.NULL_STRING_VALUE
import com.example.test_app.dto.CountryDTO
import com.example.test_app.model.Country

fun Country.transform(): CountryDTO {
    val countryDTO = CountryDTO()

    this?.let {
        countryDTO.countryName = countryName ?: NULL_STRING_VALUE
        countryDTO.cityName = cityName ?: NULL_STRING_VALUE
        countryDTO.population = population ?: NULL_INT_VALUE
        countryDTO.languages = languages ?: mutableListOf()
        countryDTO.flag = flag ?: NULL_STRING_VALUE
        countryDTO.area = area ?: NULL_DOUBLE_VALUE
        countryDTO.location = location ?: mutableListOf()
    }
    return countryDTO
}

fun MutableList<Country>.transformToMutableList(): MutableList<CountryDTO> {
    val countryListDTO = mutableListOf<CountryDTO>()

    for (countryModel in this) {
        countryListDTO.add(countryModel.transform())
    }
    return countryListDTO
}