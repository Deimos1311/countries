package com.example.data.transformers

import com.example.data.room.entity.LanguageEntity
import com.example.domain.dto.LanguageDTO
import com.example.test_app.STRING_NULL_VALUE

fun LanguageEntity.transformLanguageEntityToDto(): LanguageDTO {
    val languageDTO = LanguageDTO()

    this?.let {
        languageDTO.name = name ?: STRING_NULL_VALUE
        languageDTO.nativeName = nativeName ?: STRING_NULL_VALUE
        languageDTO.iso639_1 = iso639_1 ?: STRING_NULL_VALUE
        languageDTO.iso639_2 = iso639_2 ?: STRING_NULL_VALUE
    }
    return languageDTO
}

fun MutableList<LanguageEntity>.transformLanguageEntityMutableListToDto(): MutableList<LanguageDTO> {
    val languageMutableListDTO = mutableListOf<LanguageDTO>()

    for (languageEntity in this) {
        languageMutableListDTO.add(languageEntity.transformLanguageEntityToDto())
    }
    return languageMutableListDTO
}

fun LanguageDTO.transformLanguageDtoToEntity(): LanguageEntity {
    val languageEntity = LanguageEntity()

    this?.let {
        languageEntity.name = name ?: STRING_NULL_VALUE
        languageEntity.nativeName = nativeName ?: STRING_NULL_VALUE
        languageEntity.iso639_1 = iso639_1 ?: STRING_NULL_VALUE
        languageEntity.iso639_2 = iso639_2 ?: STRING_NULL_VALUE
    }
    return languageEntity
}

fun MutableList<LanguageDTO>.transformLanguageDtoMutableListToEntity(): MutableList<LanguageEntity> {
    val languageEntityMutableList = mutableListOf<LanguageEntity>()

    for (languageDto in this) {
        languageEntityMutableList.add(languageDto.transformLanguageDtoToEntity())
    }
    return languageEntityMutableList
}