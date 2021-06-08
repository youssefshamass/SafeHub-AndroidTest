package com.youssefshamass.domain.mapper

import com.youssefshamass.core.mapper.Mapper
import com.youssefshamass.data.entities.local.Following
import com.youssefshamass.domain.entities.UserHeader

class FollowingToUserHeader : Mapper<Following, UserHeader>() {
    override fun map(source: Following): UserHeader =
        UserHeader(
            source.loginName,
            source.avatarImageUrl
        )
}