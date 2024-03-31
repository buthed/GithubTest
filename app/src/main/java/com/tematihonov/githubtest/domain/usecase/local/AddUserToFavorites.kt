package com.tematihonov.githubtest.domain.usecase.local

import com.tematihonov.githubtest.data.local.FavoritesUserEntity
import com.tematihonov.githubtest.domain.repository.RoomRepository
import javax.inject.Inject

class AddUserToFavorites @Inject constructor(
    private val roomRepository: RoomRepository,
) {

    suspend fun invoke(favoritesUserEntity: FavoritesUserEntity) {
        return roomRepository.addUserToFavorite(favoritesUserEntity)
    }
}