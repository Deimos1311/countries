package com.it_academy.data.transformers

import com.it_academy.data.room.entity.CountryLanguageCrossRefEntity
import com.it_academy.domain.STRING_NULL_VALUE
import com.it_academy.domain.dto.countries.CountryLanguageCrossRefDTO

fun CountryLanguageCrossRefEntity.transformCountryLanguageCrossRefToDto(): CountryLanguageCrossRefDTO {
    val countryLanguageCrossRefDTO = CountryLanguageCrossRefDTO()

    this?.let {
        countryLanguageCrossRefDTO.countryName = countryName ?: STRING_NULL_VALUE
        countryLanguageCrossRefDTO.languageName = languageName ?: STRING_NULL_VALUE
    }
    return countryLanguageCrossRefDTO
}

fun MutableList<CountryLanguageCrossRefEntity>.transformCountryLanguageCrossRefMutableListYoDto():
        MutableList<CountryLanguageCrossRefDTO> {
    val countryLanguageCrossRefDTOMutableList = mutableListOf<CountryLanguageCrossRefDTO>()

    for (countryLanguageCrossRefEntity in this) {
        countryLanguageCrossRefDTOMutableList.add(
            countryLanguageCrossRefEntity
                .transformCountryLanguageCrossRefToDto()
        )
    }
    return countryLanguageCrossRefDTOMutableList
}

fun CountryLanguageCrossRefDTO.transformCountryLanguageCrossRefDtoToEntity(): CountryLanguageCrossRefEntity {
    val countryLanguageCrossRefEntity = CountryLanguageCrossRefEntity()

    this?.let {
        countryLanguageCrossRefEntity.countryName = countryName ?: STRING_NULL_VALUE
        countryLanguageCrossRefEntity.languageName = languageName ?: STRING_NULL_VALUE
    }
    return countryLanguageCrossRefEntity
}

fun MutableList<CountryLanguageCrossRefDTO>.transformCountryLanguageCrossRefDtoToEntity(): MutableList<CountryLanguageCrossRefEntity> {
    val countryLanguageCrossRefEntity = mutableListOf<CountryLanguageCrossRefEntity>()

    for (countryLanguageCrossRefDTO in this) {
        countryLanguageCrossRefEntity.add(
            countryLanguageCrossRefDTO.transformCountryLanguageCrossRefDtoToEntity()
        )
    }
    return countryLanguageCrossRefEntity
}