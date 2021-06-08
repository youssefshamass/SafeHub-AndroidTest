package com.youssefshamass.domain.mapper

import com.youssefshamass.core.mapper.Mapper
import com.youssefshamass.data.entities.local.Follower
import com.youssefshamass.domain.entities.UserHeader

class FollowerToUserHeader : Mapper<Follower, UserHeader>() {
    override fun map(source: Follower): UserHeader =
        UserHeader(
            source.loginName,
            source.avatarImageUrl
        )
}