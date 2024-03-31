package com.tematihonov.githubtest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tematihonov.githubtest.utils.RoomConstants.FAVORITE_USERS_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface GitHubTestDao {

    @Query("SELECT * FROM $FAVORITE_USERS_TABLE")
    fun getAllFavoritesUsers(): Flow<List<FavoritesUserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserToFavorite(favoritesUser: FavoritesUserEntity)

    @Query("DELETE FROM $FAVORITE_USERS_TABLE WHERE login = :userLogin")
    suspend fun deleteUserFromFavorite(userLogin: String)

    @Query("SELECT * FROM $FAVORITE_USERS_TABLE WHERE login = :userLogin")
    suspend fun checkUsersOnContainsInTable(userLogin: String): List<FavoritesUserEntity>
}