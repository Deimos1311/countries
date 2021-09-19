package com.it_academy.data.transformers

import com.it_academy.data.flow_transformer.Transformer
import com.it_academy.data.models.newsAPI.SourceModel
import com.it_academy.domain.STRING_NOT_AVAILABLE
import com.it_academy.domain.dto.news.SourceDTO

class SourceModelToSourceDtoTransformer :
    Transformer<MutableList<SourceModel>, MutableList<SourceDTO>> {
    override var convert: (MutableList<SourceModel>) -> MutableList<SourceDTO> =
        { data ->
            data.map {
                SourceDTO(
                    id = it.id ?: STRING_NOT_AVAILABLE,
                    name = it.name ?: STRING_NOT_AVAILABLE,
                    description = it.description ?: STRING_NOT_AVAILABLE,
                    url = it.url ?: STRING_NOT_AVAILABLE,
                    category = it.category ?: STRING_NOT_AVAILABLE,
                    language = it.language ?: STRING_NOT_AVAILABLE,
                    country = it.country ?: STRING_NOT_AVAILABLE
                )
            }.toMutableList()
        }
}