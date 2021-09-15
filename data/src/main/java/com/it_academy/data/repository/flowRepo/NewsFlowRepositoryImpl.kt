package com.it_academy.data.repository.flowRepo

import com.it_academy.data.flow_transformer.Transformer
import com.it_academy.data.flow_transformer.modifyFlow
import com.it_academy.data.models.newsAPI.SourceModel
import com.it_academy.data.network.flow_service.news.NewsFlowService
import com.it_academy.domain.dto.news.SourceDTO
import com.it_academy.domain.outcome.Outcome
import com.it_academy.domain.repository.NewsFlowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsFlowRepositoryImpl(
    private val newsFlowService: NewsFlowService,
    private val sourceModelToSourceDTOTransformer: Transformer<MutableList<SourceModel>, MutableList<SourceDTO>>
) : NewsFlowRepository {

    override fun getAllNews(): Flow<Outcome<MutableList<SourceDTO>>> =
        modifyFlow(newsFlowService.getAllNews().map { it.sources }, sourceModelToSourceDTOTransformer)
}