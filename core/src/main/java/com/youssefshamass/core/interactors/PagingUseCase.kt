package com.youssefshamass.core.interactors

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

@ExperimentalCoroutinesApi
abstract class PagingUseCase<P : PagingUseCase.Parameters, T : Any>
    : SubjectUseCase<P, PagingData<T>>() {
    interface Parameters {
        val config: PagingConfig
    }

    override fun invoke(parameters: P?) {
        try {
            super.invoke(parameters)
        } catch (exception: Exception) {
            Timber.e(exception)
        }
    }

    fun refresh(parameters: P?) {
        paramState.value = parameters
    }
}