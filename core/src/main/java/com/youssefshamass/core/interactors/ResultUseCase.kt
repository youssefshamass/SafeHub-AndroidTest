/*
 * Designed and developed by 2020 Youssef Shamass
 */
package com.youssefshamass.core.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import timber.log.Timber

abstract class ResultUseCase<in P, O> {
    protected abstract suspend fun doWork(parameters: P?): O

    fun execSync(parameters: P?): O = runBlocking {
        doWork(parameters)
    }

    operator fun invoke(parameters: P?): Flow<O> = flow {
        try {
            emit(doWork(parameters))
        } catch (exception: Exception) {
            Timber.e(exception)
        }
    }
}
