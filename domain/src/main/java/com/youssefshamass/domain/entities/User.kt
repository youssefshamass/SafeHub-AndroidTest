package com.youssefshamass.domain.entities

data class User (
    val id: Int,
    val loginName: String,
    val avatarImageUrl: String,
    val displayName: String?,
    val bio: String?,
    val numberOfFollowers: Int = 0,
    val numberOfFollowings: Int
)