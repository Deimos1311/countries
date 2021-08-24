package com.example.data.transformers

import com.example.data.room.entity.CountryEntity
import com.example.domain.dto.CountryDTO
import com.example.test_app.NULL_INT_VALUE
import com.example.test_app.STRING_NULL_VALUE

fun CountryEntity.transformCountryEntityToDto(): CountryDTO {
    val countryDTO = CountryDTO()

    this?.let {
        countryDTO.countryName = countryName ?: STRING_NULL_VALUE
        countryDTO.cityName = cityName ?: STRING_NULL_VALUE
        countryDTO.population = population ?: NULL_INT_VALUE
    }
    return countryDTO
}

fun MutableList<CountryEntity>.transformCountryEntityMutableListToDto(): MutableList<CountryDTO> {
    val countryDTOMutableList = mutableListOf<CountryDTO>()

    for (countryEntity in this) {
        countryDTOMutableList.add(countryEntity.transformCountryEntityToDto())
    }
    return countryDTOMutableList
}

fun CountryDTO.transformCountryDtoToEntity(): CountryEntity {
    val countryEntity = CountryEntity()

    this?.let {
        countryEntity.countryName = countryName
        countryEntity.cityName = cityName
        countryEntity.population = population
    }
    return countryEntity
}

fun MutableList<CountryDTO>.transformCountryDtoMutableListToEntity(): MutableList<CountryEntity> {
    val countryEntityMutableList = mutableListOf<CountryEntity>()

    for (countryDto in this) {
        countryEntityMutableList.add(countryDto.transformCountryDtoToEntity())
    }
    return countryEntityMutableList
}