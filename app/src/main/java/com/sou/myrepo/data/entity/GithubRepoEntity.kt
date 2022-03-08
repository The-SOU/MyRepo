package com.sou.myrepo.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GithubRepository")
data class GithubRepoEntity(
    @PrimaryKey val fullName: String,
    @Embedded val owner: GithubOwner
    )
