/*
 * Designed and developed by 2020 Youssef Shamass
 */
package com.youssefshamass.core.errors

class NetworkError : Exception()

class NotFoundError: Exception()

class GenericError(
        validationMessage: String?,
) : Exception(validationMessage)
