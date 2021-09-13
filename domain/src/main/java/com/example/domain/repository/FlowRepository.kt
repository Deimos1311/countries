package com.example.domain.repository

import com.example.domain.dto.CapitalDTO
import com.example.domain.dto.RegionDTO
import com.example.domain.outcome.Outcome
import kotlinx.coroutines.flow.Flow

interface FlowRepository {

    fun getCapitals() : Flow<Outcome<MutableList<CapitalDTO>>>

    fun getCapitalByName(name: String) : Flow<Outcome<MutableList<CapitalDTO>>>
}