package com.it_academy.data.transformers

import com.it_academy.data.models.countriesAPI.CountryModel
import com.it_academy.domain.NULL_DOUBLE_VALUE
import com.it_academy.domain.NULL_INT_VALUE
import com.it_academy.domain.STRING_NULL_VALUE
import com.it_academy.domain.dto.countries.CountryDTO

fun CountryModel.transformCountryModelToDto(): CountryDTO {
    val countryDTO = CountryDTO()

    this?.let {
        countryDTO.countryName = countryName ?: STRING_NULL_VALUE
        countryDTO.cityName = cityName ?: STRING_NULL_VALUE
        countryDTO.population = population ?: NULL_INT_VALUE
        countryDTO.languages = (languages ?: mutableListOf()).transformLanguageModelMutableListToDto()
        countryDTO.flag = flag ?: STRING_NULL_VALUE
        countryDTO.area = area ?: NULL_DOUBLE_VALUE
        countryDTO.location = location ?: mutableListOf()
    }
    return countryDTO
}

fun MutableList<CountryModel>.transformCountryModelMutableListToDto(): MutableList<CountryDTO> {
    val countryListDTO = mutableListOf<CountryDTO>()

    for (countryModel in this) {
        countryListDTO.add(countryModel.transformCountryModelToDto())
    }
    return countryListDTO
}