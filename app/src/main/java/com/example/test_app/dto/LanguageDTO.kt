package com.example.test_app.dto

import com.example.test_app.NULL_STRING_VALUE

data class LanguageDTO(

    var iso639_1: String = NULL_STRING_VALUE,
    var iso639_2: String = NULL_STRING_VALUE,
    var name: String = NULL_STRING_VALUE,
    var nativeName: String = NULL_STRING_VALUE

)
