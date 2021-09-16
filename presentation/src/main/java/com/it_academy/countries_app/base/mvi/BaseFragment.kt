package com.it_academy.countries_app.base.mvi

import android.os.Bundle
import org.koin.androidx.scope.ScopeFragment

abstract class BaseFragment<INTENT : ViewIntent, ACTION : ViewAction, STATE : ViewState,
        VM : MviBaseViewModel<INTENT, ACTION, STATE>> :
    IViewRender<STATE>, ScopeFragment() {
    private lateinit var viewState: STATE
    val mState get() = viewState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    abstract fun initData()
}
