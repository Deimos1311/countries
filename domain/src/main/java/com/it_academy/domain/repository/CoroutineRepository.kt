package com.it_academy.domain.repository

import com.it_academy.domain.dto.countries.RegionDTO

interface CoroutineRepository {

    suspend fun getRegions(): MutableList<RegionDTO>
}