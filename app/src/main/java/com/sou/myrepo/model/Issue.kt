package com.sou.myrepo.model

import com.google.gson.annotations.SerializedName

data class Issue(
    @SerializedName("number") val number: Long,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)
