package com.youssefshamass.data.datasources.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.youssefshamass.core.data.local.IDAO
import com.youssefshamass.data.entities.local.Following

@Dao
interface FollowingDAO : IDAO<Following> {
    @Query("SELECT * FROM user_followings WHERE user_id = :forUserId")
    fun paging(forUserId: Int): PagingSource<Int, Following>

    @Query("DELETE FROM user_followings WHERE user_id = :forUserId")
    fun clear(forUserId: Int): Int
}