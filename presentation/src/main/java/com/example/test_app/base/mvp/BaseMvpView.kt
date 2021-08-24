package com.example.test_app.base.mvp

interface BaseMvpView {

    fun showError(error: String)

    fun showProgress()

    fun hideProgress()
}

