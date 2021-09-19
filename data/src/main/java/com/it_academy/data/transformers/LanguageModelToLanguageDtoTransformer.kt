package com.it_academy.data.transformers

import com.it_academy.data.models.countriesAPI.LanguageModel
import com.it_academy.domain.STRING_NOT_AVAILABLE
import com.it_academy.domain.dto.countries.LanguageDTO

fun LanguageModel.transformLanguageModelToDto(): LanguageDTO {
    val languageDTO = LanguageDTO()

    this?.let {
        languageDTO.iso639_1 = iso639_1 ?: STRING_NOT_AVAILABLE
        languageDTO.iso639_2 = iso639_2 ?: STRING_NOT_AVAILABLE
        languageDTO.name = name ?: STRING_NOT_AVAILABLE
        languageDTO.nativeName = nativeName ?: STRING_NOT_AVAILABLE
    }
    return languageDTO
}

fun MutableList<LanguageModel>.transformLanguageModelMutableListToDto(): MutableList<LanguageDTO> {
    val languageListDTO = mutableListOf<LanguageDTO>()

    for (languageModel in this) {
        languageListDTO.add(languageModel.transformLanguageModelToDto())
    }
    return languageListDTO
}