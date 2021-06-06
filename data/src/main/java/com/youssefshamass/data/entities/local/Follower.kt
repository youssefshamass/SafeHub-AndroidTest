package com.youssefshamass.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "user_followers",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Follower(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "login_name")
    val loginName: String,
    @ColumnInfo(name = "avatar_image_url")
    val avatarImageUrl: String
) {
    constructor(userId: Int, loginName: String, avatarImageUrl: String) : this(
        0,
        userId,
        loginName,
        avatarImageUrl
    )
}