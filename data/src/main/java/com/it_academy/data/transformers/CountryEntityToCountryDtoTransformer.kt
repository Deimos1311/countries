package com.it_academy.data.transformers

import com.it_academy.data.room.entity.CountryEntity
import com.it_academy.domain.INT_NOT_AVAILABLE
import com.it_academy.domain.STRING_NOT_AVAILABLE
import com.it_academy.domain.dto.countries.CountryDTO

fun CountryEntity.transformCountryEntityToDto(): CountryDTO {
    val countryDTO = CountryDTO()

    this?.let {
        countryDTO.countryName = countryName ?: STRING_NOT_AVAILABLE
        countryDTO.cityName = cityName ?: STRING_NOT_AVAILABLE
        countryDTO.population = population ?: INT_NOT_AVAILABLE
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