package com.it_academy.domain.usecase

abstract class UseCaseUnit<Params: Any, Result> {

    private var mParams: Params? = null

    protected abstract fun buildUnit(params: Params?)

    abstract val isParamsRequired: Boolean

    fun setParams(params: Params): UseCaseUnit<Params, Result>{
        mParams = params
        return this
    }

    fun execute() {
        require(!(isParamsRequired && mParams == null)) {"Params are required"}
        buildUnit(mParams)
    }
}