package com.youssefshamass.data.datasources.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.youssefshamass.core.data.local.IDAO
import com.youssefshamass.data.entities.local.Follower

@Dao
interface FollowerDAO : IDAO<Follower> {
    @Query("SELECT * FROM user_followers WHERE user_id = :forUserId")
    fun paging(forUserId: Int): PagingSource<Int, Follower>

    @Query("DELETE FROM user_followers WHERE user_id = :forUserId")
    fun clear(forUserId: Int) : Int
}