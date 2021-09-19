package com.it_academy.data.transformers

import com.it_academy.data.models.countriesAPI.CountryModel
import com.it_academy.domain.DOUBLE_NOT_AVAILABLE
import com.it_academy.domain.INT_NOT_AVAILABLE
import com.it_academy.domain.STRING_NOT_AVAILABLE
import com.it_academy.domain.dto.countries.CountryDTO

fun CountryModel.transformCountryModelToDto(): CountryDTO {
    val countryDTO = CountryDTO()

    this?.let {
        countryDTO.countryName = countryName ?: STRING_NOT_AVAILABLE
        countryDTO.cityName = cityName ?: STRING_NOT_AVAILABLE
        countryDTO.population = population ?: INT_NOT_AVAILABLE
        countryDTO.languages = (languages ?: mutableListOf()).transformLanguageModelMutableListToDto()
        countryDTO.flag = flag ?: STRING_NOT_AVAILABLE
        countryDTO.area = area ?: DOUBLE_NOT_AVAILABLE
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