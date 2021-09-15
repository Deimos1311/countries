package com.it_academy.data.transformers

import com.it_academy.data.flow_transformer.Transformer
import com.it_academy.data.models.countriesAPI.CapitalModel
import com.it_academy.domain.STRING_NULL_VALUE
import com.it_academy.domain.dto.countries.CapitalDTO

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