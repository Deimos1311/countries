package com.it_academy.domain.dto.news

import com.it_academy.domain.STRING_NOT_AVAILABLE

data class SourceDTO(

    var id: String = STRING_NOT_AVAILABLE,
    var name: String = STRING_NOT_AVAILABLE,
    var description: String = STRING_NOT_AVAILABLE,
    var url: String = STRING_NOT_AVAILABLE,
    var category: String = STRING_NOT_AVAILABLE,
    var language: String = STRING_NOT_AVAILABLE,
    var country: String = STRING_NOT_AVAILABLE

)