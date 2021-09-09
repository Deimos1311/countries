package com.example.test_app.fragments.list_of_capitals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.domain.dto.CapitalDTO
import com.example.domain.usecase.impl.coroutine.GetAllCapitalsFromAPICoroutineUseCase
import com.example.test_app.base.mvvm.BaseViewModel
import com.example.test_app.base.mvvm.Outcome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//todo coroutine job as livedata outcome obj
class ListOfCapitalsViewModel(
    savedStateHandle: SavedStateHandle,
    private val mGetAllCapitalsFromAPICoroutineUseCase: GetAllCapitalsFromAPICoroutineUseCase
): BaseViewModel(savedStateHandle) {

    var dataCapitalsLiveData = MutableLiveData<Outcome<MutableList<CapitalDTO>>>()

    fun getCapitals() {
        dataCapitalsLiveData.postValue(Outcome.loading(true))

        CoroutineScope(viewModelScope.coroutineContext + Dispatchers.IO).launch {
            try {
                val result = mGetAllCapitalsFromAPICoroutineUseCase.execute()
                dataCapitalsLiveData.postValue(Outcome.loading(false))
                dataCapitalsLiveData.postValue(Outcome.next(result))
            } catch (e: Exception) {
                dataCapitalsLiveData.postValue(Outcome.loading(false))
            }
        }
    }
}