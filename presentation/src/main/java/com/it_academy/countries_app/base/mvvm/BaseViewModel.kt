package com.it_academy.countries_app.base.mvvm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun addToDisposable(disposable: Disposable?) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}