package com.youssefshamass.data.entities.mappers

import com.youssefshamass.core.mapper.Mapper
import com.youssefshamass.data.entities.local.Follower
import com.youssefshamass.data.entities.local.Following
import com.youssefshamass.data.entities.remote.UserHeader

class GithubUserHeaderToFollowing(private val baseUserId: Int) : Mapper<UserHeader, Following>() {
    override fun map(source: UserHeader): Following = Following(
        baseUserId,
        source.loginName,
        source.avatarImageUrl
    )
}