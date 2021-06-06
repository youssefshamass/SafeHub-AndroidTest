package com.youssefshamass.data.datasources.local

import androidx.room.Dao
import androidx.room.Query
import com.youssefshamass.core.data.local.IDAO
import com.youssefshamass.data.entities.local.SearchQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchQueryDAO : IDAO<SearchQuery> {
    @Query("SELECT * FROM recent_search_queries")
    fun getAll(): Flow<List<SearchQuery>>

    @Query(
        """DELETE FROM recent_search_queries WHERE id NOT IN (SELECT id FROM recent_search_queries 
            ORDER BY id DESC LIMIT 5)"""
    )
    suspend fun purgeObsoleteEntries()
}