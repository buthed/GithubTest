package com.tematihonov.githubtest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tematihonov.githubtest.utils.RoomConstants.FAVORITE_USERS_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface GitHubTestDao {

//    // Search
//    @Query("SELECT * FROM $SEARCH_USERS_TABLE")
//    fun getAllSearchUsers(limit: Int, offset: Int): Flow<List<SearchUserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserToSearh(searchUser: SearchUserEntity)

    // Favorites
    @Query("SELECT * FROM $FAVORITE_USERS_TABLE")
    fun getAllFavoritesUsers(): Flow<List<FavoritesUserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUserToFavorite(favoritesUser: FavoritesUserEntity)

    @Query("DELETE FROM $FAVORITE_USERS_TABLE WHERE login = :userLogin")
    suspend fun deleteUserFromFavorite(userLogin: String)

    @Query("SELECT * FROM $FAVORITE_USERS_TABLE WHERE login = :userLogin")
    suspend fun checkUsersOnContainsInTable(userLogin: String): List<FavoritesUserEntity>
}