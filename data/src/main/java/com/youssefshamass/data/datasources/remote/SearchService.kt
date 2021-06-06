package com.youssefshamass.data.datasources.remote

import com.youssefshamass.data.entities.remote.UserHeader
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/users")
    suspend fun search(@Query("q") query: String): List<UserHeader>
}