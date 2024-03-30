package com.tematihonov.githubtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchUserEntity::class, FavoritesUserEntity::class], version = 1, exportSchema = false)
abstract class GitHubTestDataBase: RoomDatabase() {

    abstract fun gitHubTestDao(): GitHubTestDao
}