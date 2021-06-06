/*
 * Designed and developed by 2020 Youssef Shamass
 */
package com.youssefshamass.core.data.base

sealed class InvokeStatus<out T> {
    data class Success<T>(val data: T?) : InvokeStatus<T>()
    data class Error<T>(val error: Throwable, val message: String? = null) : InvokeStatus<T>()
    class Loading<T> : InvokeStatus<T>()
}
