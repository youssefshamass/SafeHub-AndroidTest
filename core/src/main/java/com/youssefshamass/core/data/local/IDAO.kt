package com.youssefshamass.core.data.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface IDAO<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(model: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg model: T): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(model: T)
}