package com.youssefshamass.core.mapper

abstract class Mapper<F, T> {
    abstract fun map(source: F): T

    fun collection(source: List<F>): List<T> =
        source.map { map(it) }
}