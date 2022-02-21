package com.my.snapask.model.api.vo

import com.google.gson.annotations.SerializedName

data class UserItem(
    @SerializedName("id")
    val id: Long,

    @SerializedName("avatar_url")
    val avatar_url: String? = null,

    @SerializedName("login")
    val login: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("company")
    val company: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("hireable")
    val hireable: Boolean? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("bio")
    val bio: String? = null,
)

