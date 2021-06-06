package com.youssefshamass.data.datasources.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.youssefshamass.core.data.local.IDAO
import com.youssefshamass.data.entities.local.Following
import com.youssefshamass.data.entities.remote.UserHeader

@Dao
interface FollowingDAO : IDAO<Following> {
    @Query("DELETE FROM user_followings WHERE user_id = :forUserId")
    fun paging(forUserId: Int): PagingSource<Int, UserHeader>

    @Query("DELETE FROM user_followings WHERE user_id = :forUserId")
    suspend fun clear(forUserId: Int)
}