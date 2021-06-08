package com.youssefshamass.data.db

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.youssefshamass.core.database.DatabaseTransactionRunner

class RoomTransactionRunner(
    private val db: ApplicationDatabase
) : DatabaseTransactionRunner {
    override suspend fun <T> invoke(block: suspend () -> T): T =
        db.withTransaction {
            block()
        }
}