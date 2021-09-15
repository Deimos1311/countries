package com.it_academy.domain.repository

import com.it_academy.domain.dto.news.SourceDTO
import com.it_academy.domain.outcome.Outcome
import kotlinx.coroutines.flow.Flow

interface NewsFlowRepository {

    fun getAllNews() : Flow<Outcome<MutableList<SourceDTO>>>
}