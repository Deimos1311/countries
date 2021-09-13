package com.example.test_app.fragments.region

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.dto.RegionDTO
import com.example.domain.outcome.Outcome
import com.example.domain.usecase.impl.coroutine.GetAllRegionsFromAPICoroutineUseCase
import com.example.test_app.base.mvvm.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegionViewModel(
    savedStateHandle: SavedStateHandle,
    private val mGetAllRegionsFromAPICoroutineUseCase: GetAllRegionsFromAPICoroutineUseCase
): BaseViewModel(savedStateHandle) {

    var regionsLiveData = MutableLiveData<Outcome<MutableList<RegionDTO>>>()

    fun getRegions() {
        regionsLiveData.postValue(Outcome.loading(true))

        CoroutineScope(viewModelScope.coroutineContext + Dispatchers.IO).launch {
            try {
                val result = mGetAllRegionsFromAPICoroutineUseCase.execute()
                regionsLiveData.postValue(Outcome.loading(false))
                regionsLiveData.postValue(Outcome.next(result))
            } catch (ex: Exception) {
                ex.printStackTrace()
                regionsLiveData.postValue(Outcome.loading(false))
            }
        }
    }
}