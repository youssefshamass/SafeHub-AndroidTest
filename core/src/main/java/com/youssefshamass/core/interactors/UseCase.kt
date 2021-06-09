/*
 * Designed and developed by 2020 Youssef Shamass
 */
package com.youssefshamass.core.interactors

import com.youssefshamass.core.data.base.InvokeStatus
import com.youssefshamass.core.errors.GenericError
import com.youssefshamass.core.errors.NetworkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit

abstract class UseCase<I : Any, O> {
    operator fun invoke(
        parameters: I,
        timeoutMS: Long = DEFAULT_TIMEOUT_MS
    ): Flow<InvokeStatus<O>> {
        return flow {
            emit(InvokeStatus.Loading<O>())
            try {
                val response = doWork(parameters)

                emit(InvokeStatus.Success<O>(response))
            } catch (error: Exception) {
                Timber.e(error)

                when (error) {
                    is GenericError -> emit(InvokeStatus.Error<O>(error))
                    is IOException -> emit(InvokeStatus.Error<O>(NetworkError()))
                    is HttpException -> {
                        val message = error.message()
                            ?: "Something wrong happened, please try again in few moments"
                        emit(
                            InvokeStatus.Error<O>(
                                GenericError(
                                    message
                                )
                            )
                        )
                    }
                    else -> emit(InvokeStatus.Error<O>(error, "Something wrong happened"))
                }
            }
        }
    }

    suspend fun execSync(parameters: I): O =
        doWork(parameters)

    protected abstract suspend fun doWork(parameters: I): O

    companion object {
        private val DEFAULT_TIMEOUT_MS = TimeUnit.MINUTES.toMillis(3)
    }
}
