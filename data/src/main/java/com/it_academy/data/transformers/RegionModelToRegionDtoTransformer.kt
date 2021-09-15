package com.it_academy.data.transformers

import com.it_academy.data.flow_transformer.Transformer
import com.it_academy.data.models.countriesAPI.RegionModel
import com.it_academy.domain.STRING_NULL_VALUE
import com.it_academy.domain.dto.countries.RegionDTO

class RegionModelToRegionDtoTransformer :
    Transformer<MutableList<RegionModel>, MutableList<RegionDTO>> {

    override var convert: (MutableList<RegionModel>) -> MutableList<RegionDTO> =
        { data ->
            data.map {
                RegionDTO(
                    region = it.region ?: STRING_NULL_VALUE
                )
            }.toMutableList()
        }
}