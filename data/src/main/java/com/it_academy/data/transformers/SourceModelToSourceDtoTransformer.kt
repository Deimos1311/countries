package com.it_academy.data.transformers

import com.it_academy.data.flow_transformer.Transformer
import com.it_academy.data.models.newsAPI.SourceModel
import com.it_academy.domain.STRING_NULL_VALUE
import com.it_academy.domain.dto.news.SourceDTO

class SourceModelToSourceDtoTransformer :
    Transformer<MutableList<SourceModel>, MutableList<SourceDTO>> {
    override var convert: (MutableList<SourceModel>) -> MutableList<SourceDTO> =
        { data ->
            data.map {
                SourceDTO(
                    id = it.id ?: STRING_NULL_VALUE,
                    name = it.name ?: STRING_NULL_VALUE,
                    description = it.description ?: STRING_NULL_VALUE,
                    url = it.url ?: STRING_NULL_VALUE,
                    category = it.category ?: STRING_NULL_VALUE,
                    language = it.language ?: STRING_NULL_VALUE,
                    country = it.country ?: STRING_NULL_VALUE
                )
            }.toMutableList()
        }
}