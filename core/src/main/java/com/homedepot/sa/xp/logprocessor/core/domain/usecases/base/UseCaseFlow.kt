package com.homedepot.sa.xp.logprocessor.core.domain.usecases.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class UseCaseFlow<T, in Input>(private val background: CoroutineDispatcher) {
    protected abstract  fun run(input: Input? = null): Flow<T>
    operator fun invoke(input: Input? = null, onFailure: (Exception) -> Unit): Flow<T> = flow {
        try {
            run(input)
                .collect { value ->
                    emit(value)
                }
        } catch (error: Exception) {
            onFailure(error)
        }

    }.flowOn(background)
}