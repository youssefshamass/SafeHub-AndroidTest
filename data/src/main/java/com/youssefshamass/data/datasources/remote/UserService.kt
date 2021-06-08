package com.youssefshamass.data.datasources.remote

import com.youssefshamass.data.entities.remote.User
import com.youssefshamass.data.entities.remote.UserHeader
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("users/{login_name}")
    suspend fun getUserDetails(@Path("login_name") loginName: String): User

    @GET("users/{login_name}/following")
    suspend fun getFollowings(
        @Path("login_name") loginName: String,
        @Query("page") page: Int = 1,
        @Query("per_page") pageSize: Int = 15
    ): List<UserHeader>

    @GET("users/{login_name}/followers")
    suspend fun getFollowers(
        @Path("login_name") loginName: String,
        @Query("page") page: Int = 1,
        @Query("per_page") pageSize: Int = 15
    ): List<UserHeader>
}