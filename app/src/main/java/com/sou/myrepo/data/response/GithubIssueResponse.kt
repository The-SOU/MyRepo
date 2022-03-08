package com.sou.myrepo.data.response

import com.google.gson.annotations.SerializedName
import com.sou.myrepo.data.entity.GithubIssueEntity

data class GithubIssueResponse(
    @SerializedName("number") val number: Long,
    @SerializedName("title") val title: String,
    @SerializedName("body") val description: String,
    @SerializedName("user") val items: GithubIssueEntity
)
