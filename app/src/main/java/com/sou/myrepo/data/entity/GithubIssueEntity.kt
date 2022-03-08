package com.sou.myrepo.data.entity

import com.google.gson.annotations.SerializedName

data class GithubIssueEntity(
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("id") val userId: Long,
    @SerializedName("login") val userName: String
)
