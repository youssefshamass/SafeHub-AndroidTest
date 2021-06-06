/*
 * Designed and developed by 2020 Youssef Shamass
 */
package com.youssefshamass.core.interactors

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

@ExperimentalCoroutinesApi
abstract class SubjectUseCase<P, O> {
    protected val paramState = MutableStateFlow<P?>(null)

    open operator fun invoke(parameters: P?) {
        paramState.value = parameters
    }

    protected abstract fun createObservable(parameters: P?): Flow<O>

    fun observe(): Flow<O> =
            paramState./*filter { it != null }.*/flatMapLatest { createObservable(it) }
}
