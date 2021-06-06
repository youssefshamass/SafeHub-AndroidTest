package com.youssefshamass.data.db

import androidx.room.Database
import com.youssefshamass.data.datasources.local.FollowerDAO
import com.youssefshamass.data.datasources.local.FollowingDAO
import com.youssefshamass.data.datasources.local.UserDAO
import com.youssefshamass.data.entities.local.Follower
import com.youssefshamass.data.entities.local.Following
import com.youssefshamass.data.entities.local.User

@Database(
    entities = [
        User::class,
        Follower::class,
        Following::class,
    ],
    version = 1,
    exportSchema = false
)

abstract class ApplicationDatabase {
    abstract fun users() : UserDAO
    abstract fun followers() : FollowerDAO
    abstract fun followings() : FollowingDAO

    companion object {
        const val DATABASE_NAME = "safe_hub"
    }
}