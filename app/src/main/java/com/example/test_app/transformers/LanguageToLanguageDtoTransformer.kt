package com.example.test_app.transformers

import com.example.test_app.NULL_STRING_VALUE
import com.example.test_app.dto.LanguageDTO
import com.example.test_app.model.Language

fun Language.transform(): LanguageDTO {
    val languageDTO = LanguageDTO()

    this?.let {
        languageDTO.iso639_1 = iso639_1 ?: NULL_STRING_VALUE
        languageDTO.iso639_2 = iso639_2 ?: NULL_STRING_VALUE
        languageDTO.name = name ?: NULL_STRING_VALUE
        languageDTO.nativeName = nativeName ?: NULL_STRING_VALUE
    }
    return languageDTO
}

fun MutableList<Language>.transformToMutableListDto(): MutableList<LanguageDTO> {
    val languageListDTO = mutableListOf<LanguageDTO>()

    for (languageModel in this) {
        languageListDTO.add(languageModel.transform())
    }
    return languageListDTO
}