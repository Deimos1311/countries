package com.example.data.transformers

import com.example.domain.STRING_NULL_VALUE
import com.example.domain.dto.LanguageDTO

fun com.example.data.model.LanguageModel.transformLanguageModelToDto(): LanguageDTO {
    val languageDTO = LanguageDTO()

    this?.let {
        languageDTO.iso639_1 = iso639_1 ?: STRING_NULL_VALUE
        languageDTO.iso639_2 = iso639_2 ?: STRING_NULL_VALUE
        languageDTO.name = name ?: STRING_NULL_VALUE
        languageDTO.nativeName = nativeName ?: STRING_NULL_VALUE
    }
    return languageDTO
}

fun MutableList<com.example.data.model.LanguageModel>.transformLanguageModelMutableListToDto(): MutableList<LanguageDTO> {
    val languageListDTO = mutableListOf<LanguageDTO>()

    for (languageModel in this) {
        languageListDTO.add(languageModel.transformLanguageModelToDto())
    }
    return languageListDTO
}