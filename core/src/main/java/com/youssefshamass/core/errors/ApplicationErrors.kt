/*
 * Designed and developed by 2020 Youssef Shamass
 */
package com.youssefshamass.core.errors

class NetworkError : Exception()

class GenericError(
        val code: Int?,
        validationMessage: String?,
        val result: Result<*>? = null
) : Exception(validationMessage)