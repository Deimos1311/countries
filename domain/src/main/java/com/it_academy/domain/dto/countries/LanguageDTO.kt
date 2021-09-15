package com.it_academy.domain.dto.countries

import com.it_academy.domain.STRING_NULL_VALUE

data class LanguageDTO(

    var name: String = STRING_NULL_VALUE,
    var nativeName: String = STRING_NULL_VALUE,
    var iso639_1: String = STRING_NULL_VALUE,
    var iso639_2: String = STRING_NULL_VALUE

)
