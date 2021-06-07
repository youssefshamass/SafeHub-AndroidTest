package com.youssefshamass.data.entities.local

import androidx.room.*

@Entity(
    tableName = "user_followings",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index("user_id")
    ]
)
data class Following(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "login_name")
    val loginName: String,
    @ColumnInfo(name = "avatar_image_url")
    val avatarImageUrl: String
) {
    @Ignore
    constructor(userId: Int, loginName: String, avatarImageUrl: String) : this(
        0,
        userId,
        loginName,
        avatarImageUrl
    )
}