package com.youssefshamass.data.entities.mappers

import com.youssefshamass.core.mapper.Mapper
import com.youssefshamass.data.entities.local.Follower
import com.youssefshamass.data.entities.remote.UserHeader

class GithubUserHeaderToFollower(private val baseUserId: Int) : Mapper<UserHeader, Follower>() {
    override fun map(source: UserHeader): Follower = Follower(
        baseUserId,
        source.loginName,
        source.avatarImageUrl
    )
}