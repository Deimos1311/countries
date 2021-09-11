package com.example.test_app.fragments.region

import androidx.lifecycle.SavedStateHandle
import com.example.data.repository.flowRepo.FlowRepositoryImpl
import com.example.domain.dto.RegionDTO
import com.example.domain.outcome.Outcome
import com.example.domain.repository.FlowRepository
import com.example.test_app.base.mvvm.BaseViewModel
import kotlinx.coroutines.flow.Flow

class RegionViewModel(
    savedStateHandle: SavedStateHandle,
    private val flowRepository: FlowRepository
): BaseViewModel(savedStateHandle) {

    fun getAllRegionsFlow(): Flow<Outcome<MutableList<RegionDTO>>> =
        flowRepository.getRegion()
}