package com.youssefshamass.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_search_queries")
data class SearchQuery(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "query")
    val query: String
) {
    constructor(query: String) : this(0, query)
}