package com.example.data.transformers

import com.example.data.room.entity.CountryLanguageCrossRefEntity
import com.example.domain.dto.CountryLanguageCrossRefDTO
import com.example.domain.STRING_NULL_VALUE

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