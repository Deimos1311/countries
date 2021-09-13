package com.example.data.transformers

import com.example.data.flow_transformer.Transformer
import com.example.data.model.CapitalModel
import com.example.domain.STRING_NULL_VALUE
import com.example.domain.dto.CapitalDTO

class CapitalModelToCapitalDtoTransformer :
    Transformer<MutableList<CapitalModel>, MutableList<CapitalDTO>> {
    override var convert: (MutableList<CapitalModel>) -> MutableList<CapitalDTO> =
        { data ->
            data.map {
                CapitalDTO(
                    capital = it.capital ?: STRING_NULL_VALUE
                )
            }.toMutableList()
        }
}