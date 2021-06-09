package com.youssefshamass.domain.mapper

import com.youssefshamass.core.mapper.Mapper
import com.youssefshamass.data.entities.local.User

class LocalUserToDomainUser : Mapper<User, com.youssefshamass.domain.entities.User>() {
    override fun map(source: User): com.youssefshamass.domain.entities.User =
        com.youssefshamass.domain.entities.User(
            source.id,
            source.loginName,
            source.avatarImageUrl,
            source.displayName,
            source.bio,
            source.numberOfFollowers,
            source.numberOfFollowings
        )
}