package com.youssefshamass.data.repositories

import com.youssefshamass.data.datasources.local.SearchQueryDAO
import com.youssefshamass.data.datasources.remote.SearchService
import com.youssefshamass.data.entities.local.SearchQuery
import com.youssefshamass.data.entities.remote.UserHeader

interface ISearchRepository {
    suspend fun searchUsersIndex(query: String): List<UserHeader>
}

class SearchRepository(
    private val searchQueryDAO: SearchQueryDAO,
    private val searchService: SearchService
) : ISearchRepository {
    override suspend fun searchUsersIndex(query: String): List<UserHeader> {
        searchQueryDAO.insert(SearchQuery(query))
        searchQueryDAO.purgeObsoleteEntries()

        return searchService.search(query)
    }
}