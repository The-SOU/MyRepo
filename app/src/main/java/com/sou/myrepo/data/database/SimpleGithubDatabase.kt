package com.sou.myrepo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sou.myrepo.data.dao.RepositoryDao
import com.sou.myrepo.data.entity.GithubRepoEntity

@Database(entities = [GithubRepoEntity::class], version = 1)
abstract class SimpleGithubDatabase : RoomDatabase() {

    abstract fun repositoryDao(): RepositoryDao
}