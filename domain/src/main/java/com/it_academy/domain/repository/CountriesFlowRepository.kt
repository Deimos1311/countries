package com.it_academy.domain.repository

import com.it_academy.domain.dto.countries.CapitalDTO
import com.it_academy.domain.outcome.Outcome
import kotlinx.coroutines.flow.Flow

interface CountriesFlowRepository {

    fun getCapitals() : Flow<Outcome<MutableList<CapitalDTO>>>

    fun getCapitalByName(name: String) : Flow<Outcome<MutableList<CapitalDTO>>>
}