package com.it_academy.domain.dto.news

import com.it_academy.domain.STRING_NULL_VALUE

data class SourceDTO(

    var id: String = STRING_NULL_VALUE,
    var name: String = STRING_NULL_VALUE,
    var description: String = STRING_NULL_VALUE,
    var url: String = STRING_NULL_VALUE,
    var category: String = STRING_NULL_VALUE,
    var language: String = STRING_NULL_VALUE,
    var country: String = STRING_NULL_VALUE

)