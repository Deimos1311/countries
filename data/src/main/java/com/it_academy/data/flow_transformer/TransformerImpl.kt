package com.it_academy.data.flow_transformer

import com.it_academy.domain.outcome.Outcome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

fun <InputType, OutputType> modifyFlow(
    data: Flow<InputType>,
    transformer: Transformer<InputType, OutputType>
): Flow<Outcome<OutputType>> {
    return data.execute(transformer.convert)
}

fun <InputType, OutputType> Flow<InputType>.execute(convert: (InputType) -> OutputType): Flow<Outcome<OutputType>> =
    this.flowOn(Dispatchers.IO)
        .map { model -> convert(model) }
        .map { list -> Outcome.success(list) }
        .onStart { emit(Outcome.loading(true)) }
        .onCompletion { emit(Outcome.loading(false)) }
        .catch { ex -> emit(Outcome.failure(ex)) }
