package com.example.domain.repository

import com.example.domain.dto.CapitalDTO

interface CoroutineRepository {

    suspend fun getCapitals(): MutableList<CapitalDTO>
}