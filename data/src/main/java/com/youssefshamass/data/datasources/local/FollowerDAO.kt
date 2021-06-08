package com.youssefshamass.data.datasources.local

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.youssefshamass.core.data.local.IDAO
import com.youssefshamass.data.entities.local.Follower
import com.youssefshamass.data.entities.local.Following
import com.youssefshamass.data.entities.remote.UserHeader

@Dao
interface FollowerDAO : IDAO<Follower> {
    @Query("SELECT * FROM user_followers WHERE user_id = :forUserId")
    fun paging(forUserId: Int): PagingSource<Int, Follower>

    @Query("DELETE FROM user_followers WHERE user_id = :forUserId")
    fun clear(forUserId: Int) : Int
}