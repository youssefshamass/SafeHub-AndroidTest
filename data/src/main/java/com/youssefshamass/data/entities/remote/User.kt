package com.youssefshamass.data.entities.remote

import com.google.gson.annotations.SerializedName
import com.youssefshamass.core.data.entities.Model
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserHeader(
    @SerializedName("login")
    val loginName: String,
    @SerializedName("avatar_url")
    val avatarImageUrl: String
) : Model()

@Parcelize
data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val loginName: String,
    @SerializedName("avatar_url")
    val avatarImageUrl: String,
    @SerializedName("name")
    val displayName: String?,
    @SerializedName("company")
    val companyName: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("followers")
    val numberOfFollowers: Int,
    @SerializedName("following")
    val numberOfFollowings: Int
) : Model()