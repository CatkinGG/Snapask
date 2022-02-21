package com.my.snapask.model.api.vo

import com.google.gson.annotations.SerializedName

data class RepoItem(
    @SerializedName("id")
    val id: Long,

    @SerializedName("full_name")
    val full_name: String,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("language")
    val language: String? = null,

    @SerializedName("html_url")
    val html_url: String? = null,

    @SerializedName("watchers_count")
    val watchers_count: Int
)

