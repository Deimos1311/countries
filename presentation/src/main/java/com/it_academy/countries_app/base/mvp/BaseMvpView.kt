package com.it_academy.countries_app.base.mvp

interface BaseMvpView {

    fun showError(error: String)

    fun showProgress()

    fun hideProgress()
}

