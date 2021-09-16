package com.it_academy.countries_app.base.mvi

interface IViewRender<STATE> {
    fun render(state: STATE)
}