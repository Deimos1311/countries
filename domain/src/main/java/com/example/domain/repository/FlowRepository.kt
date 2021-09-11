package com.example.domain.repository

import com.example.domain.dto.RegionDTO
import com.example.domain.outcome.Outcome
import kotlinx.coroutines.flow.Flow

interface FlowRepository {

    fun getRegion() : Flow<Outcome<MutableList<RegionDTO>>>
}