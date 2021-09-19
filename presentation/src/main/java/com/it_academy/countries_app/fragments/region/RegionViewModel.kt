package com.it_academy.countries_app.fragments.region

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.it_academy.countries_app.base.mvvm.BaseViewModel
import com.it_academy.domain.dto.countries.RegionDTO
import com.it_academy.domain.outcome.Outcome
import com.it_academy.domain.usecase.impl.coroutine.GetAllRegionsFromAPICoroutineUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegionViewModel(
    savedStateHandle: SavedStateHandle,
    private val mGetAllRegionsFromAPICoroutineUseCase: GetAllRegionsFromAPICoroutineUseCase
): BaseViewModel(savedStateHandle) {

    var regionsLiveData = MutableLiveData<Outcome<MutableList<RegionDTO>>>()

    fun getRegions() {

        CoroutineScope(viewModelScope.coroutineContext).launch {
            viewModelScope.launch {
                try {
                    regionsLiveData.value = Outcome.loading(true)
                    val result = withContext(Dispatchers.IO) {
                        mGetAllRegionsFromAPICoroutineUseCase.execute()
                    }
                    regionsLiveData.value = Outcome.loading(false)
                    regionsLiveData.value = Outcome.next(result)

                } catch (ex: Exception) {
                    ex.printStackTrace()
                    regionsLiveData.value = Outcome.loading(false)
                }
            }
        }
    }
}