package com.it_academy.data.transformers

import com.it_academy.data.room.entity.LanguageEntity
import com.it_academy.domain.STRING_NOT_AVAILABLE
import com.it_academy.domain.dto.countries.LanguageDTO

fun LanguageEntity.transformLanguageEntityToDto(): LanguageDTO {
    val languageDTO = LanguageDTO()

    this?.let {
        languageDTO.name = name ?: STRING_NOT_AVAILABLE
        languageDTO.nativeName = nativeName ?: STRING_NOT_AVAILABLE
        languageDTO.iso639_1 = iso639_1 ?: STRING_NOT_AVAILABLE
        languageDTO.iso639_2 = iso639_2 ?: STRING_NOT_AVAILABLE
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
        languageEntity.name = name ?: STRING_NOT_AVAILABLE
        languageEntity.nativeName = nativeName ?: STRING_NOT_AVAILABLE
        languageEntity.iso639_1 = iso639_1 ?: STRING_NOT_AVAILABLE
        languageEntity.iso639_2 = iso639_2 ?: STRING_NOT_AVAILABLE
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