package com.tematihonov.githubtest.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tematihonov.githubtest.utils.RoomConstants.FAVORITE_USERS_TABLE

@Entity(tableName = FAVORITE_USERS_TABLE)
data class FavoritesUserEntity(
    @ColumnInfo(name = "avatar_url")
    val avatar_url: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "login")
    val login: String,
)