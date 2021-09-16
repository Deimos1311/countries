package com.it_academy.countries_app.base.mvi

import androidx.lifecycle.LiveData

interface IViewModel<STATE: ViewState, INTENT: ViewIntent> {

    val state: LiveData<STATE>

    fun dispatchIntent(intent: INTENT)
}