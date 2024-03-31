package com.tematihonov.githubtest.domain.repository

import com.tematihonov.githubtest.data.local.FavoritesUserEntity
import kotlinx.coroutines.flow.Flow

interface RoomRepository {

    fun getAllFavoritesUsers(): Flow<List<FavoritesUserEntity>>

    suspend fun addUserToFavorite(favoritesUser: FavoritesUserEntity)

    suspend fun deleteUserFromFavorite(userLogin: String)

    suspend fun checkUsersOnContainsInTable(userLogin: String): List<FavoritesUserEntity>
}