package com.youssefshamass.data.entities.mappers

import com.youssefshamass.core.mapper.Mapper
import com.youssefshamass.data.entities.remote.User as RemoteUser
import com.youssefshamass.data.entities.local.User as LocalUser

class GithubUserToUser : Mapper<RemoteUser, LocalUser>() {
    override fun map(source: com.youssefshamass.data.entities.remote.User): LocalUser = LocalUser(
        source.id!!,
        source.loginName,
        source.avatarImageUrl,
        source.displayName,
        source.companyName,
        source.location,
        source.bio,
        source.numberOfFollowers,
        source.numberOfFollowings
    )
}