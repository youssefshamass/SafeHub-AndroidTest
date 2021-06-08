package com.youssefshamass.data.entities.local

import androidx.room.*

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "login_name")
    val loginName: String,
    @ColumnInfo(name = "avatar_image_url")
    val avatarImageUrl: String,
    @ColumnInfo(name = "display_name")
    val displayName: String?,
    @ColumnInfo(name = "company_name")
    val companyName: String?,
    @ColumnInfo(name = "location")
    val location: String?,
    @ColumnInfo(name = "bio")
    val bio: String?,
    @ColumnInfo(name = "number_of_followers", defaultValue = "0")
    val numberOfFollowers: Int = 0,
    @ColumnInfo(name = "number_of_followings", defaultValue = "0")
    val numberOfFollowings: Int
)