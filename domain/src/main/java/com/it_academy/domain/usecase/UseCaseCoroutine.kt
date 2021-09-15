package com.it_academy.domain.usecase


abstract class UseCaseCoroutine<Params : Any, Result> {

    private var mParams: Params? = null

    protected abstract suspend fun buildCoroutine(params: Params?): Result

    abstract val isParamsRequired: Boolean

    suspend fun setParams(params: Params): UseCaseCoroutine<Params, Result>{
        mParams = params
        return this
    }

    suspend fun execute(): Result {
        require(!(isParamsRequired && mParams == null)) {"Params are required"}
        return buildCoroutine(mParams)
    }
}