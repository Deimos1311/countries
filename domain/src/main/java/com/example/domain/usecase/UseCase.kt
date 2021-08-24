package com.example.domain.usecase

import io.reactivex.rxjava3.core.Flowable

abstract class UseCase<Params: Any, Result> {

    private var mParams: Params? = null

    protected abstract fun buildFlowable(params: Params?): Flowable<Result>

    abstract val isParamsRequired: Boolean

    fun setParams(params: Params): UseCase<Params, Result>{
        mParams = params
        return this
    }

    fun execute(): Flowable<Result>{
        require(!(isParamsRequired && mParams == null)) {"Params are required"}
        return buildFlowable(mParams)
    }
}