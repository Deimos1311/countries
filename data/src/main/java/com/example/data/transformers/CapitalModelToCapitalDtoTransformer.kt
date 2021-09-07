package com.example.data.transformers

import com.example.data.model.CapitalModel
import com.example.domain.dto.CapitalDTO
import com.example.test_app.STRING_NULL_VALUE

fun CapitalModel.transformCapitalModelToDto(): CapitalDTO {
    val capitalDTO = CapitalDTO()

        this?.let {
            capitalDTO.capital = capital ?: STRING_NULL_VALUE
        }
    return capitalDTO
}

fun MutableList<CapitalModel>.transformMutableListCapitalModelToDto(): MutableList<CapitalDTO> {
    val mutableListCapitalDTO = mutableListOf<CapitalDTO>()

    for(capitalModel in this) {
        mutableListCapitalDTO.add(capitalModel.transformCapitalModelToDto())
    }
    return mutableListCapitalDTO
}