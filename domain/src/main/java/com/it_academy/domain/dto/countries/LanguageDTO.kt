package com.it_academy.domain.dto.countries

import com.it_academy.domain.STRING_NOT_AVAILABLE

data class LanguageDTO(

    var name: String = STRING_NOT_AVAILABLE,
    var nativeName: String = STRING_NOT_AVAILABLE,
    var iso639_1: String = STRING_NOT_AVAILABLE,
    var iso639_2: String = STRING_NOT_AVAILABLE

)
