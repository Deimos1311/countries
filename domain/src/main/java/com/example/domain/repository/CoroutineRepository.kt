package com.example.domain.repository

import com.example.domain.dto.RegionDTO

interface CoroutineRepository {

    suspend fun getRegions(): MutableList<RegionDTO>
}