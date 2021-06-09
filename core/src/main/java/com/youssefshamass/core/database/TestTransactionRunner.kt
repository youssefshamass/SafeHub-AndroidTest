package com.youssefshamass.core.database

object TestTransactionRunner : DatabaseTransactionRunner {
    override suspend fun <T> invoke(block: suspend () -> T): T = block()
}