package com.example.data.flow_trasformer

import com.example.data.model.RegionModel
import com.example.domain.STRING_NULL_VALUE
import com.example.domain.dto.RegionDTO

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