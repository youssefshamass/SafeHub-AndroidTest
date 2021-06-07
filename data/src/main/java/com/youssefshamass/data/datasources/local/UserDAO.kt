package com.youssefshamass.data.datasources.local

import androidx.room.Dao
import androidx.room.Query
import com.youssefshamass.core.data.local.IDAO
import com.youssefshamass.data.entities.local.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO : IDAO<User> {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun observeUser(userId: Int) : Flow<User>

    @Query("SELECT * FROM users WHERE login_name = :loginName")
    suspend fun getUser(loginName: String): User?

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUser(userId : Int): User?

    @Query("SELECT * FROM users")
    fun getMatches() : Flow<List<User>>
}