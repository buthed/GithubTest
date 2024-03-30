package com.tematihonov.githubtest.domain.repository

import com.tematihonov.githubtest.data.local.FavoritesUserEntity
import com.tematihonov.githubtest.data.local.SearchUserEntity
import kotlinx.coroutines.flow.Flow

interface RoomRepository {

//    fun getAllSearchUsers(limit: Int, offset: Int): Flow<List<SearchUserEntity>>

    suspend fun addUserToSearh(searchUser: SearchUserEntity)

    fun getAllFavoritesUsers(): Flow<List<FavoritesUserEntity>>

    suspend fun addUserToFavorite(favoritesUser: FavoritesUserEntity)

    suspend fun deleteUserFromFavorite(userLogin: String)

    suspend fun checkUsersOnContainsInTable(userLogin: String): List<FavoritesUserEntity>
}